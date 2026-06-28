package com.example.springbootdemo.service;

import com.example.springbootdemo.entity.AttendanceMonth;
import com.example.springbootdemo.entity.AttendanceDetail;
import com.example.springbootdemo.repository.AttendanceMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.YearMonth;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceMonthRepository attendanceMonthRepository;

    /**
     * 指定されたユーザー、年、月の勤怠情報を取得する。存在しない場合は新規作成する
     * @param userId
     * @param year
     * @param month
     * @return
     */
    public AttendanceMonth getOrCreateAttendanceMonth(Long userId, Integer year, Integer month) {
        Optional<AttendanceMonth> existing = attendanceMonthRepository.findByUserIdAndYearAndMonth(userId, year, month);

        if (existing.isPresent()) {
            return existing.get();
        }

        // 新しいレコードを作成
        AttendanceMonth newMonth = new AttendanceMonth(userId, year, month);

        // その月の日数を計算して、詳細レコードを作成
        YearMonth yearMonth = YearMonth.of(year, month);
        int lastDayOfMonth = yearMonth.lengthOfMonth();

        for (int day = 1; day <= lastDayOfMonth; day++) {
            AttendanceDetail detail = new AttendanceDetail(newMonth, day);
            newMonth.getDetails().add(detail);
        }

        return attendanceMonthRepository.save(newMonth);
    }

    /**
     * 指定されたユーザー、年、月の勤怠情報を取得する
     */
    public AttendanceMonth getAttendanceMonth(Long userId, Integer year, Integer month) {
        return attendanceMonthRepository.findByUserIdAndYearAndMonth(userId, year, month)
                .orElse(null);
    }

    /**
     * 指定されたユーザー、年、月の勤怠情報が存在するかを確認する
     * @param userId
     * @param year
     * @param month
     * @return
     */
    public boolean hasAttendanceMonth(Long userId, Integer year, Integer month) {
        return attendanceMonthRepository.findByUserIdAndYearAndMonth(userId, year, month)
                .isPresent();
    }

    /**
     * 指定された年月日の曜日を取得する
     * @param year
     * @param month
     * @param day
     * @return
     */
    public String getDayOfWeekClass(Integer year, Integer month, Integer day) {
        LocalDate date = LocalDate.of(year, month, day);
        int dayOfWeek = date.getDayOfWeek().getValue();

        if (dayOfWeek == 6) {  // 土曜日
            return "saturday";
        } else if (dayOfWeek == 7) {  // 日曜日
            return "sunday";
        }
        return "weekday";
    }

    /**
     * 指定された年月日の曜日を日本語で取得する
     * @param year
     * @param month
     * @param day
     * @return
     */
    public String getDayOfWeekJapanese(Integer year, Integer month, Integer day) {
        LocalDate date = LocalDate.of(year, month, day);
        String[] dayNames = {"日", "月", "火", "水", "木", "金", "土"};
        int dayOfWeek = date.getDayOfWeek().getValue();
        int index = dayOfWeek == 7 ? 0 : dayOfWeek;
        return dayNames[index];
    }

    /**
     * 勤怠詳細を保存する
     * @param detail
     */
    @Transactional
    public void saveAttendanceDetail(AttendanceDetail detail) {
        if (detail.getId() == null) {
            detail.getAttendanceMonth().getDetails().add(detail);
        }
        attendanceMonthRepository.save(detail.getAttendanceMonth());
    }

    /**
     * 勤怠詳細を削除する
     * @param userId
     * @param year
     * @param month
     * @param day
     */
    @Transactional
    public void deleteAttendanceDetail(Long userId, Integer year, Integer month, Integer day) {
        Optional<AttendanceMonth> attendanceMonth = attendanceMonthRepository.findByUserIdAndYearAndMonth(userId, year, month);

        if (attendanceMonth.isPresent()) {
            AttendanceDetail detail = attendanceMonth.get().getDetails().stream()
                    .filter(d -> d.getDayOfMonth().equals(day))
                    .findFirst()
                    .orElse(null);

            if (detail != null) {
                // データを初期化（NULL設定）
                detail.setClassification(null);
                detail.setStartTime(null);
                detail.setEndTime(null);
                detail.setBreakMinutes(null);
                detail.setNightBreakMinutes(null);
                detail.setRemarks(null);
                detail.setApprovalStatus(null);
                attendanceMonthRepository.save(attendanceMonth.get());
            }
        }
    }
}
