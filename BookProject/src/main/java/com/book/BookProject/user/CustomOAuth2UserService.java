package com.book.BookProject.user;

import com.book.BookProject.security.UserServiceImpl;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 소셜 로그인 사용자 정보
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("id");
        String email = oAuth2User.getAttribute("email");

        // DB에서 사용자 조회
        UserEntity user = userRepository.findBySocialId(providerId).orElse(null);

        if (user == null) {
            // 사용자 정보가 없으면 추가 회원가입 페이지로 이동 (나중에 구현)
            throw new OAuth2AuthenticationException("소셜 로그인 회원가입 필요");
        }

        return oAuth2User;
    }
}