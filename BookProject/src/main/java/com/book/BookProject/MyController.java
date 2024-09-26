package com.book.BookProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
	
    @RequestMapping("/")	
    public String main() {
        return "guest/main";
    }
    @RequestMapping("/book")	
    public String book() {
        return "guest/bookboard";       
    }
    
    @RequestMapping("/board")
    public String book2() {
    	return "guest/board";
    }

    @RequestMapping("/book3")
    public String book3() {
        return "/guest/book";
    }
    
}