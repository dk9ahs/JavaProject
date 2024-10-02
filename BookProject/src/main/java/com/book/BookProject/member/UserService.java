package com.book.BookProject.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUser(UserDTO userDTO) {

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