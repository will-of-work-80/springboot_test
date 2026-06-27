package com.example.springbootdemo.service;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserByStaffCode(String staffCode) {
        return userRepository.findByStaffCode(staffCode);
    }

    public User getDefaultUser() {
        return userRepository.findAll().stream()
                .findFirst()
                .orElse(new User("〇〇 Company", "△△部署", "鄭　守正", "999999999902"));
    }

    public User createUser(String companyName, String department, String userName, String staffCode) {
        User user = new User(companyName, department, userName, staffCode);
        return userRepository.save(user);
    }

    public User updateUser(Long id, String companyName, String department, String userName) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setCompanyName(companyName);
            user.setDepartment(department);
            user.setUserName(userName);
            return userRepository.save(user);
        }
        return null;
    }
}
