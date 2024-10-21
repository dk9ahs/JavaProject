package com.book.BookProject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindServiceImpl implements FindService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private String verificationCode;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FindServiceImpl(UserRepository userRepository, MailService mailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    // 인터페이스에 정의된 메서드 구현
    @Override
    public String findIdByNameAndPhone(String name, String phone) {
        Optional<UserEntity> userOptional = userRepository.findByNameAndPhone(name, phone);
        if (userOptional.isPresent()) {
            return userOptional.get().getId();
        } else {
            throw new IllegalArgumentException("해당하는 사용자가 없습니다.");
        }
    }

    @Override
    public boolean isUserValid(String name, String email, String id) {
        return userRepository.findByNameAndEmailAndId(name, email, id).isPresent();
    }

    @Override
    public void sendVerificationCode(String email) {
        verificationCode = mailService.sendVerificationCode(email);
    }

    @Override
    public boolean verifyAuthCode(String authCode) {
        return authCode.equals(verificationCode);
    }

    @Override
    public String generateTempPasswordAndSendEmail(String email) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            String tempPassword = mailService.sendTempPassword(email);
            String encodedTempPassword = passwordEncoder.encode(tempPassword);
            user.setPwd(encodedTempPassword);
            userRepository.save(user);
            return tempPassword;
        } else {
            throw new IllegalArgumentException("일치하는 사용자를 찾을 수 없습니다.");
        }
    }
}