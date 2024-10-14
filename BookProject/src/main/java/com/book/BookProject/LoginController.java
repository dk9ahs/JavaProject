package com.book.BookProject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    // 로그인 페이지 렌더링
    @GetMapping("/login")
    public String showLoginPage(Model model, RedirectAttributes redirectAttributes) {
        // 로그인 실패 메시지 처리
        if (redirectAttributes.getFlashAttributes().containsKey("error")) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
        }
        // 로그아웃 성공 메시지 처리
        if (redirectAttributes.getFlashAttributes().containsKey("logout")) {
            model.addAttribute("logoutMessage", "로그아웃 되었습니다.");
        }
        return "guest/Login";  // 로그인 페이지 템플릿 반환
    }


}