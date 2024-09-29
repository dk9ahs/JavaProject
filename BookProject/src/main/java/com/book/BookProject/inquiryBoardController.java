package com.book.BookProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class inquiryBoardController {

    @RequestMapping("/inquiryBoard") // 문의게시판 리스트
    public  String inquiryBoard()
    {
        return "/guest/inquiryBoard";
    }

    @RequestMapping("/inquiryBoardView") // 문의게시판 내용보기
    public  String inquiryBoardView()
    {
        return "/member/inquiryBoardView";
    }

    @RequestMapping("/inquiryBoardWriteForm") // 문의게시판 글 작성
    public  String inquiryBoardWriteForm()
    {
        return "/member/inquiryBoardWriteForm";
    }
}
