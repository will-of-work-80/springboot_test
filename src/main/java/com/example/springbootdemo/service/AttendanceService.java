package com.example.springbootdemo.service;

import com.example.springbootdemo.entity.AttendanceMonth;
import com.example.springbootdemo.entity.AttendanceDetail;
import com.example.springbootdemo.repository.AttendanceMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.YearMonth;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceMonthRepository attendanceMonthRepository;

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

    public AttendanceMonth getAttendanceMonth(Long userId, Integer year, Integer month) {
        return attendanceMonthRepository.findByUserIdAndYearAndMonth(userId, year, month)
                .orElse(null);
    }

    public boolean hasAttendanceMonth(Long userId, Integer year, Integer month) {
        return attendanceMonthRepository.findByUserIdAndYearAndMonth(userId, year, month)
                .isPresent();
    }

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
}
