package com.example.springbootdemo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "attendance_details")
public class AttendanceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_month_id", nullable = false)
    private AttendanceMonth attendanceMonth;

    @Column(nullable = false)
    private Integer dayOfMonth;

    @Column(length = 50)
    private String classification;

    private LocalTime startTime;

    private LocalTime endTime;

    @Column(length = 50)
    private String approvalStatus;

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

    public AttendanceDetail() {
    }

    public AttendanceDetail(AttendanceMonth attendanceMonth, Integer dayOfMonth) {
        this.attendanceMonth = attendanceMonth;
        this.dayOfMonth = dayOfMonth;
        this.classification = "出勤";
        this.approvalStatus = "未申請";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttendanceMonth getAttendanceMonth() {
        return attendanceMonth;
    }

    public void setAttendanceMonth(AttendanceMonth attendanceMonth) {
        this.attendanceMonth = attendanceMonth;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
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
        return "AttendanceDetail{" +
                "id=" + id +
                ", dayOfMonth=" + dayOfMonth +
                ", classification='" + classification + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
