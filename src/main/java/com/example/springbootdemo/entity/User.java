package com.example.springbootdemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String staffCode;

    public User() {
    }

    public User(String companyName, String department, String userName, String staffCode) {
        this.companyName = companyName;
        this.department = department;
        this.userName = userName;
        this.staffCode = staffCode;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", department='" + department + '\'' +
                ", userName='" + userName + '\'' +
                ", staffCode='" + staffCode + '\'' +
                '}';
    }
}
