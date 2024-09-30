package com.book.BookProject.inquiryboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryBoardRepository extends JpaRepository<InquiryBoard, Long> {

}
