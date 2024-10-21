package com.book.BookProject.inquiryboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryBoardRepository extends JpaRepository<InquiryBoard, Long>
{


    @Query(value = "update INQUIRYBOARD set QVIEW_COUNT=QVIEW_COUNT+1 where qidx=:qidx", nativeQuery = true)
    void viewCount(@Param("qidx") Long qidx); // 조회수 증가

    Page<InquiryBoard> findByTitleContaining(String title, Pageable pageable);
    Page<InquiryBoard> findByContentContaining(String content, Pageable pageable);
    Page<InquiryBoard> findByNickContaining(String nick, Pageable pageable);

    // 특정 originNo의 게시글과 그 하위 답글을 모두 가져오기
    @Modifying
    @Query("SELECT b FROM InquiryBoard b WHERE b.originNo = :originNo ORDER BY b.groupOrd ASC")
    List<InquiryBoard> findRepliesByOriginNo(@Param("originNo") Long originNo);

    // originNo 기준으로 내림차순, groupOrd 기준으로 오름차순으로 정렬된 게시글 목록을 가져옴
    @Query("SELECT b FROM InquiryBoard b ORDER BY b.originNo DESC, b.groupOrd ASC")
    List<InquiryBoard> viewList();
}
