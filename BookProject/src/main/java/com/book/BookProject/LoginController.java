package com.book.BookProject;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(HttpServletRequest request) {
        return "guest/Login";
    }

//    @GetMapping("/guest/unlock")
//    public String showUnlockUserPage() {
//        return "guest/UnlockUser";  // UnlockUser.html 반환
//    }
}