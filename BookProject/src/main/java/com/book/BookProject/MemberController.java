package com.book.BookProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "member/Signup";
    }

    @GetMapping("/login")
    public String showLogInPage() {
        return "member/Login";
    }
}
