//package com.book.BookProject.member;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    // 회원 가입
//    @Transactional
//    public UserEntity registerUser(UserEntity userEntity) {
//        // 중복 검사 등 추가 로직
//        return userRepository.save(userEntity);
//    }
//
//    // 닉네임 중복 검사
//    public boolean checkNickDuplication(String nick) {
//        return userRepository.findByNick(nick) != null;
//    }
//
//    // 이메일 중복 검사
//    public boolean checkEmailDuplication(String email) {
//        return userRepository.findByEmail(email) != null;
//    }
//}
