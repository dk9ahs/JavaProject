package com.book.BookProject.user;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validateUser(String id, String password) {
        // 사용자 검증 로직
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        // 입력된 비밀번호와 DB에 저장된 비밀번호를 비교
        return user.getPwd().equals(password);
    }

    // 사용자 정보 가져오는 메서드
    public UserEntity getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }
}
