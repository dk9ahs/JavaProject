package com.book.BookProject.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsById(String id);
    boolean existsByNick(String nick);

    Optional<UserEntity> findById(String id);
}
