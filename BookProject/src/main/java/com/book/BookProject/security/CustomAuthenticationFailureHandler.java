package com.book.BookProject.security;

import com.book.BookProject.user.UserEntity;
import com.book.BookProject.user.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;

    public CustomAuthenticationFailureHandler(UserServiceImpl userServiceImpl, UserRepository userRepository) {
        this.userServiceImpl = userServiceImpl;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");

        // 사용자 엔티티 찾기
        UserEntity user = userRepository.findById(username)
                .orElseThrow(() -> new InternalAuthenticationServiceException("User not found"));

        // 실패 시도 카운트 증가
        userServiceImpl.increaseFailedAttempts(user);  // UserEntity 객체를 전달
        String redirectUrl = "/login?error=true";

        if (exception instanceof LockedException) {
            redirectUrl = "/guest/unlock";  // 계정 잠김일 경우 Unlock 페이지로 리다이렉트
        }

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}