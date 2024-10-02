package com.book.BookProject.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SignupService {

    private final UserRepository userRepository;

    public SignupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUser(UserDTO userDTO) {

        // 아이디 중복 확인
        if (!isIdUnique(userDTO.getId())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        // 닉네임 중복 확인
        if (!isNickUnique(userDTO.getNick())) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }

        // 비밀번호 확인
        if (!userDTO.getPwd().equals(userDTO.getPwdconfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 기본 권한 설정: 아이디가 "admin"이면 권한을 ROLE_ADMIN, 그 외에는 ROLE_USER
        String authority = "ROLE_USER";  // 기본적으로 일반 사용자는 ROLE_USER
        if (userDTO.getId().equalsIgnoreCase("admin")) {
            authority = "ROLE_ADMIN";  // 아이디가 "admin"이면 관리자 권한 부여
        }

        // DTO를 엔티티로 변환
        UserEntity userEntity = UserEntity.builder()
                .id(userDTO.getId())
                .nick(userDTO.getNick())
                .pwd(userDTO.getPwd())
                .name(userDTO.getName())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .zipcode(userDTO.getZipcode())
                .address(userDTO.getAddress())
                .detailAddress(userDTO.getDetailAddress())
                .createDate(LocalDateTime.now())
                .authority(authority)
                .enabled(1)
                .build();

        // 저장
        userRepository.save(userEntity);
    }

    public boolean isIdUnique(String id) {
        // 존재하지 않으면 true 반환 (유일한 경우)
        return !userRepository.existsById(id);
    }

    public boolean isNickUnique(String nick) {
        // 존재하지 않으면 true 반환 (유일한 경우)
        return !userRepository.existsByNick(nick);
    }



}