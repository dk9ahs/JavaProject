package com.book.BookProject.security;

import com.book.BookProject.user.UserEntity;
import com.book.BookProject.user.UserRepository;
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
    private final UserRepository userRepository;

    public CustomAuthenticationSuccessHandler(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();

        // 사용자 엔티티 찾기
        UserEntity user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userService.resetFailedAttempts(user);  // UserEntity 객체를 전달
        response.sendRedirect("/");  // 로그인 성공 시 리다이렉트
    }
}