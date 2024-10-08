package com.book.BookProject.sales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesBoardDTO {

    private Long sidx;  // 게시물 ID
    private String nick;  // 작성자 닉네임
    private String sbooktitle;  // 책 제목
    private String scontent;  // 게시물 내용
    private LocalDateTime screateDate = LocalDateTime.now();  // 작성일 기본값 현재 시간
    private LocalDateTime supdateDate = LocalDateTime.now();  // 수정일 기본값 현재 시간
    // 시스템이 관리하는 필드들
    private int sviewCount = 0;  // 조회 수 (기본값 0)
    private int slikeCount = 0;  // 좋아요 수 (기본값 0)
    private String oimage;  // 외부 이미지 파일 경로
    private String simage;  // 내부 이미지 파일 경로
    private String price;  // 가격

}