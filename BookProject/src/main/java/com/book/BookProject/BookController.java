package com.book.BookProject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    @GetMapping("/bookList")
    public String showBookListPage(@RequestParam String text,
                                   @RequestParam(defaultValue = "Title") String category,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   Model model) {
        // 검색 파라미터를 모델에 추가하여 뷰에서 사용
        model.addAttribute("text", text);
        model.addAttribute("category", category);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        // guest/bookList 템플릿을 반환하여 페이지를 렌더링
        return "guest/bookList";
    }

    @GetMapping("/bookdetail")
    public String getBookDetailPage(@RequestParam("isbn") String isbn, Model model) {
        // ISBN 값을 모델에 추가하여 뷰에서 사용
        model.addAttribute("isbn", isbn);
        return "guest/bookdetail";
    }

    @GetMapping("/bestseller")
    public String bestseller() {
        return "guest/bestSellerList";
    }

}