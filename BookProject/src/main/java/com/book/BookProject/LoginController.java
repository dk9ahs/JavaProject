package com.book.BookProject;

import com.book.BookProject.user.LoginService;
import com.book.BookProject.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public String showLoginPage() {
        return "member/Login";  // 로그인 페이지 템플릿 반환
    }

    @PostMapping("/login")
    public String login(@RequestParam("id") String id,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes,
                        HttpServletRequest request) {
        try {
            // 로그인 처리 로직
            if (loginService.validateUser(id, password)) {
                // 로그인 성공 시 사용자 정보로 세션 설정
                UserEntity user = loginService.getUserById(id); // 유저 정보를 가져옴

                HttpSession session = request.getSession();
                session.setAttribute("user", user.getId()); // 세션에 사용자 ID 저장

                // UserEntity의 authority 필드에서 권한을 가져옴
                if (user.getAuthority().equals("ROLE_ADMIN")) {
                    session.setAttribute("role", "ADMIN");
                } else {
                    session.setAttribute("role", "MEMBER");
                }

                return "redirect:/";  // 로그인 성공 시 메인 페이지로 리다이렉트
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
