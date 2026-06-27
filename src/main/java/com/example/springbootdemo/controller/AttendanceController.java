package com.example.springbootdemo.controller;

import com.example.springbootdemo.entity.AttendanceMonth;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.AttendanceService;
import com.example.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;
import java.time.LocalDate;

@Controller
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private UserService userService;

    @GetMapping("/attendance")
    public String attendance(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            Model model) {

        // ユーザー情報を取得
        User user = userService.getDefaultUser();
        model.addAttribute("companyName", user.getCompanyName());
        model.addAttribute("department", user.getDepartment());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("staffCode", user.getStaffCode());
        model.addAttribute("pageTitle", "WEB勤怠");

        // 表示年月を決定
        if (year == null || month == null) {
            LocalDate today = LocalDate.now();
            year = today.getYear();
            month = today.getMonthValue();
        }

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

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("monthDisplay", String.format("%04d年%02d月度", year, month));
        model.addAttribute("attendanceMonth", attendanceMonth);
        model.addAttribute("hasPreviousMonth", hasPreviousMonth);
        model.addAttribute("hasNextMonth", hasNextMonth);
        model.addAttribute("previousUrl", previousUrl);
        model.addAttribute("nextUrl", nextUrl);

        return "attendance/entry";
    }
}
