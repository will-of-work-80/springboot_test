package com.example.springbootdemo.repository;

import com.example.springbootdemo.entity.AttendanceMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceMonthRepository extends JpaRepository<AttendanceMonth, Long> {
    /**
     * 指定されたユーザー、年、月の勤怠情報を取得する
     * @param userId
     * @param year
     * @param month
     * @return
     */
    Optional<AttendanceMonth> findByUserIdAndYearAndMonth(Long userId, Integer year, Integer month);
}
