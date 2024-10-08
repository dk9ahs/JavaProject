package com.book.BookProject.salesboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SalesBoardRepository extends JpaRepository<SalesBoard, Long> {

    // 최신 게시물 순으로 불러오기
    @Query(value = "select * from SALESBOARD order by sidx desc", nativeQuery = true)
    List<SalesBoard> findSalesBoardPost();


}

