package com.book.BookProject;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
	
//    @RequestMapping("/")
//    public String main() {
//        return "guest/main";
//    }
@GetMapping("/")
public String mainPage(HttpSession session, Model model) {
    // 세션에서 사용자 역할(role)을 가져옴
    String role = (String) session.getAttribute("role");
    String userId = (String) session.getAttribute("user"); // 세션에서 사용자 ID 가져옴

    // 역할이 없으면 GUEST로 처리
    if (role == null) {
        role = "GUEST";
    }

    model.addAttribute("role", role);
    model.addAttribute("user", userId);

    return "guest/main";  // 메인 페이지로 이동
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