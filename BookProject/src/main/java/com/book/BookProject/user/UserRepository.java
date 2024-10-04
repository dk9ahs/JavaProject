package com.book.BookProject.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsById(String id);
    boolean existsByNick(String nick);

    Optional<UserEntity> findById(String id); // String 타입의 ID로 찾기

    Optional<UserEntity> findByNameAndEmail(String name, String email);

    // 이름과 전화번호로 사용자 찾기 메서드 추가
    Optional<UserEntity> findByNameAndPhone(String name, String phone);
}