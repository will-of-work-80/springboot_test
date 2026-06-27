package com.example.springbootdemo.controller;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Spring Boot + Thymeleaf Demo");
        model.addAttribute("message", "Welcome to Thymeleaf!");
        model.addAttribute("currentTime", LocalDateTime.now());

        List<String> items = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4");
        model.addAttribute("items", items);

        return "index";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", defaultValue = "Guest") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("projectName", "Spring Boot Thymeleaf Demo");
        model.addAttribute("version", "1.0.0");
        return "about";
    }

    @GetMapping("/main")
    public String main(Model model) {
        // DB からユーザー情報を取得
        User user = userService.getDefaultUser();

        model.addAttribute("companyName", user.getCompanyName());
        model.addAttribute("department", user.getDepartment());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("staffCode", user.getStaffCode());

        // メニュー項目の定義
        List<MenuItem> menuItems = Arrays.asList(
            new MenuItem("予定シフト", "scheduled-shift", null),
            new MenuItem("WEB勤怠", "web-attendance", "/attendance"),
            new MenuItem("その他勤怠", "other-attendance", null),
            new MenuItem("給与明細", "salary-slip", null),
            new MenuItem("経費申請", "expense-request", null),
            new MenuItem("雇用契約書", "employment-contract", null),
            new MenuItem("お知らせアラーム一覧", "notification-alarm", null),
            new MenuItem("申請する", "apply", null),
            new MenuItem("確認する", "confirm", null),
            new MenuItem("変更する", "change", null),
            new MenuItem("問い合わせする相談する", "consultation", null),
            new MenuItem("お仕事サポート", "work-support", null)
        );

        model.addAttribute("menuItems", menuItems);
        return "main";
    }

    public static class MenuItem {
        public String title;
        public String icon;
        public String url;

        public MenuItem(String title, String icon, String url) {
            this.title = title;
            this.icon = icon;
            this.url = url;
        }
    }
}