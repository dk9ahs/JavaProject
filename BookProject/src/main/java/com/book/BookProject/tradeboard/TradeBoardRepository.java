package com.book.BookProject.tradeboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeBoardRepository extends JpaRepository<TradeBoard, Long> {
    // 제네릭 타입 : long이 아니라 Long으로 작성
    // 기본적인 Create, Read, Update, Delete 자동으로 생성

    @Query(value = "select * from TradeBoard  order by tidx desc",
            nativeQuery = true)
    List<TradeBoard> findTradeBoardPost(); // 최신 게시물 순으로 불러오기
}
