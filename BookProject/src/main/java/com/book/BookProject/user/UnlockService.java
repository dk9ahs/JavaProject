package com.book.BookProject.user;

public interface UnlockService {

    // 계정 잠금 여부 확인
    boolean isAccountLocked(UserEntity userEntity);

    // 실패 시도 횟수 초기화
    void resetFailedAttempts(UserEntity userEntity);

    // 실패 시도 횟수 증가
    void increaseFailedAttempts(UserEntity userEntity);

    // 계정 잠금 해제
    void unlockAccount(UserEntity userEntity);

    // 계정 잠금 해제 인증번호 발송
    void sendUnlockVerificationCode(String email);

    // 인증번호 확인
    boolean verifyUnlockAuthCode(String authCode);

    // 인증번호 확인 후 임시 비밀번호 발급 및 이메일 전송
    void generateTempPasswordAndSendEmail(String email);

    // 사용자가 입력한 정보로 계정 유효성 확인
    boolean isUserValidForUnlock(String name, String email, String id, String phone);
}