package com.example.springbootdemo.controller;

import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    /**
     * トップページを表示する
     * @param session
     * @return
     */
    @GetMapping("/")
    public String index(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            return "redirect:/main";
        }
        return "index";
    }

    /**
     * ログイン処理
     * @param userId
     * @param password
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String login(
            @RequestParam String userId,
            @RequestParam String password,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Optional<User> user = userService.findByUserId(userId);

        if (user.isPresent() && userService.verifyPassword(password, user.get().getPassword())) {
            session.setAttribute("loggedInUser", user.get());
            return "redirect:/main";
        } else {
            model.addAttribute("error", "ユーザーIDまたはパスワードが正しくありません。");
            return "index";
        }
    }

    /**
     * メインページを表示する
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/main")
    public String main(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/";
        }

        // セッションからユーザー情報を取得
        User user = loggedInUser;

        model.addAttribute("companyName", user.getCompanyName());
        model.addAttribute("department", user.getDepartment());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("staffCode", user.getStaffCode());
        model.addAttribute("pageTitle", "メイン画面");

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