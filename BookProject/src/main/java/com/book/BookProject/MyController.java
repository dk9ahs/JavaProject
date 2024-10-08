package com.book.BookProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    @RequestMapping
    public String main() {
        return "guest/home";
    }

    @RequestMapping("/board2")
    public String board2() {
        return "guest/board2"; // guest/board2.html로 이동
    }

    @RequestMapping("/boardView")
    public String boardView() {
        return "guest/boardView"; // guest/boardView.html로 이동
    }
}