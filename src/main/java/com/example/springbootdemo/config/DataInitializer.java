package com.example.springbootdemo.config;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Initializing database with sample data...");

        // Check if data already exists
        if (userRepository.count() == 0) {
            logger.info("No data found. Creating sample users...");

            userRepository.save(new User("〇〇 Company", "△△部署", "秋山　◇◇", "999999999901"));
            userRepository.save(new User("〇〇 Company", "営業部", "田中　太郎", "999999999902"));
            userRepository.save(new User("〇〇 Company", "企画部", "鈴木　花子", "999999999903"));
            userRepository.save(new User("〇〇 Company", "IT部", "佐藤　次郎", "999999999904"));

            logger.info("Sample users created successfully!");
        } else {
            logger.info("Data already exists. Total users: {}", userRepository.count());
        }

        // Log all users
        logger.info("Current users in database:");
        userRepository.findAll().forEach(user ->
            logger.info("  - {} ({}, {}, {})",
                user.getUserName(),
                user.getCompanyName(),
                user.getDepartment(),
                user.getStaffCode())
        );
    }
}
