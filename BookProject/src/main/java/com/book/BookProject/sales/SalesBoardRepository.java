package com.book.BookProject.sales;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesBoardRepository extends JpaRepository<SalesBoard, Long> {
    // 추가적인 쿼리가 필요하다면 여기에 정의 가능
}