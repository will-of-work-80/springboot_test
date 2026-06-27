package com.example.springbootdemo.config;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.repository.UserRepository;
import com.example.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // テストユーザーのパスワードを初期化
        User user1 = userRepository.findByUserId("testUser").orElse(null);
        if (user1 != null) {
            user1.setPassword(userService.encodePassword("testUser01"));
            userRepository.save(user1);
        }

        // その他のテストユーザーもパスワードを初期化
        User user2 = userRepository.findByUserId("user2").orElse(null);
        if (user2 != null) {
            user2.setPassword(userService.encodePassword("user2pass"));
            userRepository.save(user2);
        }

        User user3 = userRepository.findByUserId("user3").orElse(null);
        if (user3 != null) {
            user3.setPassword(userService.encodePassword("user3pass"));
            userRepository.save(user3);
        }

        User user4 = userRepository.findByUserId("user4").orElse(null);
        if (user4 != null) {
            user4.setPassword(userService.encodePassword("user4pass"));
            userRepository.save(user4);
        }
    }
}
