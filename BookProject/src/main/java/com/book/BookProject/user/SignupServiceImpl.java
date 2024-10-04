package com.book.BookProject.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SignupServiceImpl implements SignupService{
    private final UserRepository userRepository;

    public SignupServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        String authority = "ROLE_USER";
        if (userDTO.getId().equalsIgnoreCase("admin")) {
            authority = "ROLE_ADMIN";
        }

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
