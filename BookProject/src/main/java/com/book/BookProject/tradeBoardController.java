package com.book.BookProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class tradeBoardController {

    @RequestMapping("/tradeBoard") // 교환 게시판 리스트
    public  String tradeBoard()
    {
        return "/guest/tradeBoard";
    }

    @RequestMapping("/tradeBoardView") // 교환 게시판 리스트
    public  String tradeBoardView()
    {
        return "/guest/tradeBoardView";
    }

    @RequestMapping("/tradeBoardWriteForm") // 교환 게시판 리스트
    public  String tradeBoardWriteForm()
    {
        return "/member/tradeBoardWriteForm";
    }
}
