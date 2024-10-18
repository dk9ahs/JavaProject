package com.book.BookProject.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // 받은 쪽지
    Page<Message> findByReceiverContaining(String receiver, Pageable pageable);

    // 보낸 쪽지
    Page<Message> findBySenderContaining(String sender, Pageable pageable);
    
    // 안읽은 쪽지 카운트
    long countByReceiverAndReadstatus(String receiver, Integer readstatus);

    // 쪽지 읽음으로 표시
    @Modifying
    @Query(value = "update MESSAGE set readstatus = 1 where msidx=:msidx", nativeQuery = true)
    int updateReadstatus(@Param("msidx") Long msidx); 




}
