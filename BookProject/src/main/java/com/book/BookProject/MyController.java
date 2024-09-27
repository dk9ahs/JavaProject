package com.book.BookProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
	
    @RequestMapping("/")	
    public String main() {
        return "guest/main";
    }

    @RequestMapping("/board")
    public String book2() {
    	return "guest/board";
    }

    @RequestMapping("/board2")
    public String board2() {
        return "guest/board2";
    }

    @RequestMapping("/boardView")
    public String boardView() {
        return "guest/boardView";
    }

}