package com.book.BookProject;

import com.book.BookProject.inquiryboard.InquiryBoard;
import com.book.BookProject.inquiryboard.InquiryBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class inquiryBoardController {

    @Autowired
    InquiryBoardService inquiryBoardService;

    @RequestMapping("/inquiryBoard") // 문의게시판 리스트
    public  String inquiryBoard(Model model)
    {
        List<InquiryBoard> result = inquiryBoardService.selectAll();
        model.addAttribute("inquiryBoards", result);

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
