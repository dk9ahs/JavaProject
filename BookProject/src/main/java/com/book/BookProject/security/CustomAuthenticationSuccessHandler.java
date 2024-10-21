package com.book.BookProject.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserServiceImpl userService;

    public CustomAuthenticationSuccessHandler(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();  // 로그인 성공한 ID

        // 로그인 성공한 경우 실패 시도 초기화 및 lastLoginDate 업데이트
        if (username != null) {
            userService.resetFailedAttempts(username);
            userService.updateLastLoginDate(username);  // 마지막 로그인 시간 업데이트
        }

        response.sendRedirect("/");  // 로그인 성공 후 리다이렉트
    }
}