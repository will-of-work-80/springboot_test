package com.example.springbootdemo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "attendance_months")
public class AttendanceMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(name = "wk_year", nullable = false)
    private Integer year;

    @Column(name = "wk_month", nullable = false)
    private Integer month;

    @OneToMany(mappedBy = "attendanceMonth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttendanceDetail> details = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }

    public AttendanceMonth() {
    }

    public AttendanceMonth(Long userId, Integer year, Integer month) {
        this.userId = userId;
        this.year = year;
        this.month = month;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public List<AttendanceDetail> getDetails() {
        return details;
    }

    public void setDetails(List<AttendanceDetail> details) {
        this.details = details;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "AttendanceMonth{" +
                "id=" + id +
                ", userId=" + userId +
                ", year=" + year +
                ", month=" + month +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
