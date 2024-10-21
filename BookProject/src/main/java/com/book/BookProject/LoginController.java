package com.book.BookProject;

import com.book.BookProject.user.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "redirectUrl", required = false) String redirectUrl, HttpServletRequest request) {
        if (redirectUrl != null && !redirectUrl.isEmpty()) {
            // 세션에 redirectUrl 저장
            request.getSession().setAttribute("redirectUrl", redirectUrl);
        }
        return "guest/Login";  // 로그인 페이지로 이동
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "redirectUrl", required = false) String redirectUrl,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {
        try {
            // 로그인 처리 로직
            if (loginService.validateUser(username, password)) {
                // 로그인 성공 시 redirectUrl이 있으면 해당 경로로 리다이렉트
                if (redirectUrl != null && !redirectUrl.isEmpty()) {
                    return "redirect:" + redirectUrl;
                } else {
                    return "redirect:/";  // 기본 경로로 리다이렉트
                }
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
                return "redirect:/login";  // 실패 시 로그인 페이지로 다시 리다이렉트
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 중 오류가 발생했습니다.");
            return "redirect:/login";
        }
    }
}