package com.book.BookProject.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignupServiceImpl implements SignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void registerUser(UserDTO userDTO) {
        if (!isIdUnique(userDTO.getId())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }
        if (!isNickUnique(userDTO.getNick())) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }
        if (!userDTO.getPwd().equals(userDTO.getPwdconfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String authority = "USER";
        if (userDTO.getId().equalsIgnoreCase("admin")) {
            authority = "ADMIN";
        }

        UserEntity userEntity = UserEntity.builder()
                .id(userDTO.getId())
                .nick(userDTO.getNick())
                .pwd(passwordEncoder.encode(userDTO.getPwd()))
                .name(userDTO.getName())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .zipcode(userDTO.getZipcode())
                .address(userDTO.getAddress())
                .detailAddress(userDTO.getDetailAddress())
                .authority(authority)
                .enabled(1)
                .build();

        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void registerSocialUser(UserDTO userDTO) {
        // 소셜 로그인 사용자의 경우 비밀번호를 'SOCIAL_LOGIN'으로 저장
        userDTO.setPwd("SOCIAL_LOGIN");

        UserEntity userEntity = UserEntity.builder()
                .id(userDTO.getId())
                .nick(userDTO.getNick())
                .pwd(userDTO.getPwd())  // 소셜 로그인 사용자는 비밀번호가 'SOCIAL_LOGIN'
                .name(userDTO.getName())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .zipcode(userDTO.getZipcode())
                .address(userDTO.getAddress())
                .detailAddress(userDTO.getDetailAddress())
                .socialId(userDTO.getSocialId())  // 소셜 아이디 저장
                .socialProvider(userDTO.getSocialProvider())  // 소셜 제공자 저장
                .authority("USER")
                .enabled(1)
                .build();

        userRepository.save(userEntity);
    }
    @Override
    public boolean isIdUnique(String id) {
        return !userRepository.existsById(id);
    }

    @Override
    public boolean isNickUnique(String nick) {
        return !userRepository.existsByNick(nick);
    }
}