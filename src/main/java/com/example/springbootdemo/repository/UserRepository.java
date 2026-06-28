package com.example.springbootdemo.repository;

import com.example.springbootdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * スタッフコードでユーザーを取得する
     * @param staffCode
     * @return
     */
    Optional<User> findByStaffCode(String staffCode);
    /**
     * ユーザーIDでユーザーを取得する
     * @param userId
     * @return
     */
    Optional<User> findByUserId(String userId);
}
