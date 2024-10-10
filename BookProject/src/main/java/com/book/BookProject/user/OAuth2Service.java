package com.book.BookProject.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OAuth2Service {

    private final UserRepository userRepository;

    public OAuth2Service(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 현재 로그인한 사용자의 정보를 가져오는 메서드 (소셜 로그인 포함)
    public UserDTO getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            String socialProvider = oAuth2User.getAttribute("socialProvider"); // 소셜 제공자 구분
            String socialId = getSocialIdFromProvider(oAuth2User, socialProvider);  // 소셜 사용자 ID 가져오기

            UserEntity user = userRepository.findBySocialId(socialId).orElse(null);

            if (user != null) {
                return convertEntityToDTO(user);
            }
        }

        return null;
    }
    // 소셜 제공자에 따라 소셜 사용자 ID를 가져오는 메서드
    private String getSocialIdFromProvider(OAuth2User oAuth2User, String provider) {
        switch (provider) {
            case "google":
                return oAuth2User.getAttribute("sub");  // 구글의 경우 'sub'
            case "naver":
                return ((Map<String, Object>) oAuth2User.getAttribute("response")).get("id").toString();  // 네이버의 경우 'response.id'
            case "kakao":
                return oAuth2User.getAttribute("id");  // 카카오는 'id'
            default:
                throw new IllegalArgumentException("Unknown social provider: " + provider);
        }
    }

    // UserEntity -> UserDTO로 변환하는 메서드
    private UserDTO convertEntityToDTO(UserEntity user) {
        return UserDTO.builder()
                .id(user.getId())
                .nick(user.getNick())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .zipcode(user.getZipcode())
                .address(user.getAddress())
                .detailAddress(user.getDetailAddress())
                .socialId(user.getSocialId())
                .build();
    }
}