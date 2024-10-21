package com.book.BookProject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UnlockServiceImpl implements UnlockService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    private String unlockVerificationCode;

    @Autowired
    public UnlockServiceImpl(UserRepository userRepository, MailService mailService, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public boolean isUserValidForUnlock(String name, String email, String id, String phone) {
        return userRepository.findByNameAndEmailAndIdAndPhone(name, email, id, phone).isPresent();
    }

    @Override
    public void sendUnlockVerificationCode(String email) {
        unlockVerificationCode = mailService.sendVerificationCode(email);  // 단일 인자로 수정
    }

    @Override
    public boolean verifyUnlockAuthCode(String authCode) {
        return authCode.equals(unlockVerificationCode);
    }

    @Override
    public void unlockAccount(UserEntity user) {
        user.setAccountLocked(0);  // 계정 잠금 해제
        user.setFailedAttempts(0);  // 실패 시도 초기화
        userRepository.save(user);
    }

    @Override
    public void generateTempPasswordAndSendEmail(String email) {
        String tempPassword = UUID.randomUUID().toString().substring(0, 12); // 12자리 임시 비밀번호 생성
        String encodedPassword = passwordEncoder.encode(tempPassword); // 비밀번호 암호화

        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setPwd(encodedPassword);  // 암호화된 비밀번호 저장
            userRepository.save(user);

            mailService.sendTempPassword(email);  // 두 번째 인자 제거
        } else {
            throw new IllegalArgumentException("해당 이메일로 사용자를 찾을 수 없습니다.");
        }
    }

    @Override
    public boolean isAccountLocked(UserEntity userEntity) {
        return userEntity.getAccountLocked() == 1;
    }

    @Override
    public void resetFailedAttempts(UserEntity userEntity) {
        userEntity.setFailedAttempts(0);
    }

    @Override
    public void increaseFailedAttempts(UserEntity userEntity) {
        int newFailedAttempts = userEntity.getFailedAttempts() + 1;
        userEntity.setFailedAttempts(newFailedAttempts);

        if (newFailedAttempts >= 3) {
            userEntity.setAccountLocked(1);  // 3회 실패 시 계정 잠금
        }
    }

}