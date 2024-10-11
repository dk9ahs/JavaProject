package com.book.BookProject.oauth2;

import com.book.BookProject.user.UserEntity;
import com.book.BookProject.user.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // Social provider (Google, Naver, Kakao)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 login's PK

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        System.out.println("Saving or updating user: " + attributes.getEmail());

        UserEntity userEntity = saveOrUpdate(attributes);

        return new CustomOAuth2User(userEntity, oAuth2User.getAttributes());
    }

    private UserEntity saveOrUpdate(OAuthAttributes attributes) {
        return userRepository.findBySocialId(attributes.getSocialId())
                .map(user -> {
                    // 이미 존재하는 유저의 경우 이메일과 소셜 이메일을 업데이트
                    user.updateSocialEmail(attributes.getEmail());  // 이메일 업데이트
                    user.setSocialEmail(attributes.getEmail());     // 소셜 이메일 업데이트
                    return userRepository.save(user);               // 변경 사항 저장
                })
                .orElseGet(() -> {
                    // 새로운 유저 저장
                    UserEntity newUser = attributes.toEntity();
                    newUser.setSocialEmail(attributes.getEmail());  // 소셜 이메일 설정
                    return userRepository.save(newUser);            // 새로운 유저 저장
                });
    }
}