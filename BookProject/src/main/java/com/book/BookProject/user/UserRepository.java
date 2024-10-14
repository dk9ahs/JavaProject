package com.book.BookProject.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 사용자의 아이디(ID)가 존재하는지 확인 (아이디 중복 체크용)
    boolean existsById(String id);

    // 사용자의 닉네임(Nick)이 존재하는지 확인 (닉네임 중복 체크용)
    boolean existsByNick(String nick);

    // 아이디로 사용자 찾기
    Optional<UserEntity> findById(String id);

    // 이름과 전화번호로 사용자 찾기 메서드 추가
    Optional<UserEntity> findByNameAndPhone(String name, String phone);

    // 닉네임으로 유저 찾기
    Optional<UserEntity> findByNick(String nick);

    // 이름, 이메일, 아이디로 사용자 찾기 (비밀번호 찾기 기능 등에서 사용 가능)
    Optional<UserEntity> findByNameAndEmailAndId(String name, String email, String id);

    // 이메일로 유저 찾기
    Optional<UserEntity> findByEmail(String email);

    // 소셜 아이디로 유저 찾기
    Optional<UserEntity> findBySocialId(String socialId);

}