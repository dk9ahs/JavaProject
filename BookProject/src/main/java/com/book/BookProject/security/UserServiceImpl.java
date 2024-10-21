package com.book.BookProject.security;

import com.book.BookProject.user.UnlockService;
import com.book.BookProject.user.UserEntity;
import com.book.BookProject.user.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UnlockService unlockService;  // UnlockService 주입


    public UserServiceImpl(UserRepository userRepository, @Lazy UnlockService unlockService) {
        this.userRepository = userRepository;
        this.unlockService = unlockService;  // UnlockService 주입

    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        if (userEntity.getAccountLocked() == 1) {
            throw new LockedException("계정이 잠겼습니다. 관리자에게 문의하세요.");
        }

        return new CustomUserDetails(userEntity);
    }

    // 마지막 로그인 시간 업데이트 메서드
    public void updateLastLoginDate(String id) {
        UserEntity userEntity = findUserEntityById(id);
        userEntity.setLastLoginDate(LocalDateTime.now());  // 현재 시간으로 업데이트
        userRepository.save(userEntity);  // 변경 사항 저장
    }

    // 유저 엔티티 검색 (중복 로직을 제거)
    private UserEntity findUserEntityById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }
    // 실패 시도 횟수 증가 메서드 (UnlockService에 위임)
    public void increaseFailedAttempts(UserEntity userEntity) {
        unlockService.increaseFailedAttempts(userEntity);  // UnlockService에서 처리
    }
    // 실패 시도 초기화 메서드 (UnlockService에 위임)
    public void resetFailedAttempts(UserEntity userEntity) {
        unlockService.resetFailedAttempts(userEntity);  // UnlockService에서 처리
    }

}