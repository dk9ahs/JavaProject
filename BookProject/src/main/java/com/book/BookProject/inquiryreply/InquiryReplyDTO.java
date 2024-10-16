package com.book.BookProject.inquiryreply;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InquiryReplyDTO {
    private Long idx; // 인덱스
    private int parentIdx; // FK, FK, 부모인덱스(문의게시판 인덱스)
    private String nick; // 닉네임
    private String title; // 제목
    private String content; // 내용
    private String pass; // 글 비밀번호
    private LocalDate createDate; // 작성일
    private LocalDate updateDate; // 수정일
    private int viewCount; // 조회수
    private String ofile; // 원본 파일명
    private String sfile; // 저장된 파일명
}
