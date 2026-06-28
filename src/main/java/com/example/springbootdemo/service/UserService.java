package com.example.springbootdemo.service;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<User> getUserByStaffCode(String staffCode) {
        return userRepository.findByStaffCode(staffCode);
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * デフォルトのユーザーを取得する。存在しない場合は新規作成する
     * @return
     */
    public User getDefaultUser() {
        return userRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    User user = new User();
                    user.setCompanyName("〇〇 Company");
                    user.setDepartment("△△部署");
                    user.setUserName("◇◇　守正");
                    user.setStaffCode("999999999902");
                    user.setUserId("defaultUser");
                    user.setPassword(encodePassword("defaultPass"));
                    return user;
                });
    }

    /**
     * ユーザーを作成する
     * @param companyName
     * @param department
     * @param userName
     * @param staffCode
     * @param userId
     * @param password
     * @return
     */
    public User createUser(String companyName, String department, String userName, String staffCode, String userId, String password) {
        User user = new User(companyName, department, userName, staffCode, userId, encodePassword(password));
        return userRepository.save(user);
    }

    /**
     * ユーザー情報を更新する
     * @param id
     * @param companyName
     * @param department
     * @param userName
     * @return
     */
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
