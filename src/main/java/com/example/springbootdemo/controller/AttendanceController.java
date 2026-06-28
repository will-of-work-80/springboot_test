package com.example.springbootdemo.controller;

import com.example.springbootdemo.entity.AttendanceMonth;
import com.example.springbootdemo.entity.AttendanceDetail;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.time.LocalTime;
import java.time.LocalDate;

@Controller
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * 勤怠情報を表示する
     * @param year
     * @param month
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/attendance")
    public String attendance(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            HttpSession session,
            Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        // ログインしていない場合はトップページにリダイレクト
        if (loggedInUser == null) {
            return "redirect:/";
        }

        // セッションからユーザー情報を取得
        User user = loggedInUser;
        model.addAttribute("companyName", user.getCompanyName());
        model.addAttribute("department", user.getDepartment());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("staffCode", user.getStaffCode());
        model.addAttribute("pageTitle", "WEB勤怠");

        // 表示年月を決定
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        if (year == null || month == null) {
            year = currentYear;
            month = currentMonth;
        }

        // 当月かどうかを判定
        boolean isCurrentMonth = (year == currentYear && month == currentMonth);
        model.addAttribute("isCurrentMonth", isCurrentMonth);

        // 勤怠情報を取得（なければ作成）
        AttendanceMonth attendanceMonth = attendanceService.getOrCreateAttendanceMonth(user.getId(), year, month);

        // 前月・来月の存在確認
        boolean hasPreviousMonth = attendanceService.hasAttendanceMonth(user.getId(),
            month == 1 ? year - 1 : year,
            month == 1 ? 12 : month - 1);

        boolean hasNextMonth = attendanceService.hasAttendanceMonth(user.getId(),
            month == 12 ? year + 1 : year,
            month == 12 ? 1 : month + 1);

        // 前月・来月のURL
        String previousUrl = month == 1
            ? "/attendance?year=" + (year - 1) + "&month=12"
            : "/attendance?year=" + year + "&month=" + (month - 1);

        String nextUrl = month == 12
            ? "/attendance?year=" + (year + 1) + "&month=1"
            : "/attendance?year=" + year + "&month=" + (month + 1);

        // 各日の曜日情報とフォーマット済み日付を計算
        java.time.LocalDate dateForWeek = java.time.LocalDate.of(year, month, 1);
        java.util.Map<Integer, String> dayOfWeekMap = new java.util.HashMap<>();
        java.util.Map<Integer, String> formattedDateMap = new java.util.HashMap<>();
        String[] dayNames = {"日", "月", "火", "水", "木", "金", "土"};

        for (int day = 1; day <= attendanceMonth.getDetails().size(); day++) {
            dateForWeek = java.time.LocalDate.of(year, month, day);
            int dayOfWeek = dateForWeek.getDayOfWeek().getValue();
            int index = dayOfWeek == 7 ? 0 : dayOfWeek;
            dayOfWeekMap.put(day, dayNames[index]);
            formattedDateMap.put(day, String.format("%02d", day));
        }

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("monthDisplay", String.format("%04d年%02d月度", year, month));
        model.addAttribute("attendanceMonth", attendanceMonth);
        model.addAttribute("dayOfWeekMap", dayOfWeekMap);
        model.addAttribute("formattedDateMap", formattedDateMap);
        model.addAttribute("hasPreviousMonth", hasPreviousMonth);
        model.addAttribute("hasNextMonth", hasNextMonth);
        model.addAttribute("previousUrl", previousUrl);
        model.addAttribute("nextUrl", nextUrl);

        return "attendance/entry";
    }

    /**
     * 勤怠情報を編集する
     * @param year
     * @param month
     * @param day
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/attendance/edit/{year}/{month}/{day}")
    public String edit(
            @PathVariable Integer year,
            @PathVariable Integer month,
            @PathVariable Integer day,
            HttpSession session,
            Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/";
        }

        // セッションからユーザー情報を取得
        User user = loggedInUser;
        model.addAttribute("companyName", user.getCompanyName());
        model.addAttribute("department", user.getDepartment());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("staffCode", user.getStaffCode());
        model.addAttribute("pageTitle", "勤怠情報入力");

        // 勤怠情報を取得
        AttendanceMonth attendanceMonth = attendanceService.getOrCreateAttendanceMonth(user.getId(), year, month);

        // 指定日の勤怠詳細を取得
        AttendanceDetail detail = attendanceMonth.getDetails().stream()
                .filter(d -> d.getDayOfMonth().equals(day))
                .findFirst()
                .orElse(null);

        // 曜日を取得
        java.time.LocalDate dateForDisplay = java.time.LocalDate.of(year, month, day);
        int dayOfWeek = dateForDisplay.getDayOfWeek().getValue();
        String[] dayNames = {"日", "月", "火", "水", "木", "金", "土"};
        int index = dayOfWeek == 7 ? 0 : dayOfWeek;
        String dayOfWeekDisplay = dayNames[index];

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);
        model.addAttribute("dateDisplay", String.format("%04d年%02d月%02d日（%s）", year, month, day, dayOfWeekDisplay));
        model.addAttribute("attendanceDetail", detail);

        return "attendance/edit";
    }

    /**
     * 勤怠情報を保存する
     * @param year
     * @param month
     * @param day
     * @param classification
     * @param startTime
     * @param endTime
     * @param breakMinutes
     * @param nightBreakMinutes
     * @param remarks
     * @param session
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/attendance/save")
    public String save(
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestParam Integer day,
            @RequestParam String classification,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) Integer breakMinutes,
            @RequestParam(required = false) Integer nightBreakMinutes,
            @RequestParam(required = false) String remarks,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/";
        }

        // セッションからユーザー情報を取得
        User user = loggedInUser;

        // 勤怠情報を取得
        AttendanceMonth attendanceMonth = attendanceService.getOrCreateAttendanceMonth(user.getId(), year, month);

        // 指定日の勤怠詳細を取得
        AttendanceDetail detail = attendanceMonth.getDetails().stream()
                .filter(d -> d.getDayOfMonth().equals(day))
                .findFirst()
                .orElse(null);

        if (detail != null) {
            // データを更新
            detail.setClassification(classification);

            if (startTime != null && !startTime.isEmpty() && startTime.length() == 4) {
                try {
                    int hour = Integer.parseInt(startTime.substring(0, 2));
                    int minute = Integer.parseInt(startTime.substring(2, 4));
                    detail.setStartTime(LocalTime.of(hour, minute));
                } catch (Exception e) {
                    detail.setStartTime(null);
                }
            } else {
                detail.setStartTime(null);
            }

            if (endTime != null && !endTime.isEmpty() && endTime.length() == 4) {
                try {
                    int hour = Integer.parseInt(endTime.substring(0, 2));
                    int minute = Integer.parseInt(endTime.substring(2, 4));
                    detail.setEndTime(LocalTime.of(hour, minute));
                } catch (Exception e) {
                    detail.setEndTime(null);
                }
            } else {
                detail.setEndTime(null);
            }

            detail.setBreakMinutes(breakMinutes);
            detail.setNightBreakMinutes(nightBreakMinutes);
            detail.setRemarks(remarks);
            detail.setApprovalStatus("申請");

            // DB に保存
            attendanceService.saveAttendanceDetail(detail);
        }

        redirectAttributes.addFlashAttribute("message", "勤怠情報を保存しました。");
        return "redirect:/attendance?year=" + year + "&month=" + month;
    }

    /**
     * 勤怠情報を削除する
     * @param year
     * @param month
     * @param day
     * @param session
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/attendance/delete")
    public String delete(
            @RequestParam Integer year,
            @RequestParam Integer month,
            @RequestParam Integer day,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/";
        }

        // セッションからユーザー情報を取得
        User user = loggedInUser;

        // 勤怠情報を削除
        attendanceService.deleteAttendanceDetail(user.getId(), year, month, day);

        redirectAttributes.addFlashAttribute("message", "勤怠情報を削除しました。");
        return "redirect:/attendance?year=" + year + "&month=" + month;
    }
}
