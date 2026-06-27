package com.example.springbootdemo.repository;

import com.example.springbootdemo.entity.AttendanceMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceMonthRepository extends JpaRepository<AttendanceMonth, Long> {
    Optional<AttendanceMonth> findByUserIdAndYearAndMonth(Long userId, Integer year, Integer month);
}
