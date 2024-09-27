package com.book.BookProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class inquiryboardController {

    @RequestMapping("/inquiryboard") // 문의게시판 리스트
    public  String inquiryboard()
    {
        return "/guest/inquiryboard";
    }

    @RequestMapping("/inquiryboardView") // 문의게시판 내용보기
    public  String inquiryboardView()
    {
        return "/member/inquiryboardView";
    }
    
    @RequestMapping("/inquiryboardWriteForm") // 문의게시판 글 작성
    public  String inquiryboardWriteForm()
    {
        return "/member/inquiryboardWriteForm";
    }
}
