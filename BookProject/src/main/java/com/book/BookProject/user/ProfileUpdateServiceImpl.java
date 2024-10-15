package com.book.BookProject.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileUpdateServiceImpl implements ProfileUpdateService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileUpdateServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 정보 가져오기 (프로필 업데이트 시 사용)
    @Override
    public UserDTO getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return convertToDto(userEntity);
    }

    // 회원 정보 업데이트
    @Transactional
    @Override
    public void updateUserProfile(UserDTO userDTO, String username) {
        UserEntity userEntity = userRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 현재 비밀번호 확인 (새 비밀번호 변경 시)
        if (userDTO.getPwd() != null && !userDTO.getPwd().isEmpty()) {
            // 현재 비밀번호와 DB에 저장된 비밀번호 비교
            if (!passwordEncoder.matches(userDTO.getCurrentPwd(), userEntity.getPwd())) {
                throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
            }

            // 비밀번호 확인 필드 체크
            if (!userDTO.getPwd().equals(userDTO.getPwdconfirm())) {
                throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
            }

            // 새 비밀번호 저장 (암호화)
            userEntity.setPwd(passwordEncoder.encode(userDTO.getPwd()));
        }

        // 닉네임, 이메일, 전화번호 등 업데이트
        userEntity.setNick(userDTO.getNick());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setZipcode(userDTO.getZipcode());
        userEntity.setAddress(userDTO.getAddress());
        userEntity.setDetailAddress(userDTO.getDetailAddress());

        userRepository.save(userEntity); // DB에 업데이트된 정보를 저장
    }

    // Entity -> DTO 변환
    private UserDTO convertToDto(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .nick(userEntity.getNick())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .zipcode(userEntity.getZipcode())
                .address(userEntity.getAddress())
                .detailAddress(userEntity.getDetailAddress())
                .build();
    }
}