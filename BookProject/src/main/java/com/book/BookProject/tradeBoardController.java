package com.book.BookProject;

import com.book.BookProject.tradeboard.TradeBoard;
import com.book.BookProject.tradeboard.TradeBoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class tradeBoardController {

    @Autowired
    TradeBoardService tradeBoardService;

    @RequestMapping("/tradeBoard") // 교환 게시판 리스트
    public  String tradeBoard(Model model)
    {
//        List<TradeBoard> result = tradeBoardService.selectAll();
//        model.addAttribute("tradeBoardPost", result);

        List<TradeBoard> result = tradeBoardService.selectBoardPost();
        model.addAttribute("tradeBoardPost", result);

        return "/guest/tradeBoard";
    }

    @RequestMapping("/tradeBoardView") // 교환 게시글 상세보기
    public  String tradeBoardView()
    {
        return "/guest/tradeBoardView";
    }

    @RequestMapping("/tradeBoardWriteForm") // 교환 게시판 리스트
    public  String tradeBoardWriteForm()
    {
        return "/member/tradeBoardWriteForm";
    }

    @RequestMapping("/tradeBoardWrite") // 교환 게시판 글 작성
    public  String tradeBoardWrite(HttpServletRequest request, Model model)
    {
        String nick = request.getParameter("nick");
        String classification = request.getParameter("classification");
        String region = request.getParameter("region");
        String title = request.getParameter("title");
        String price = request.getParameter("price");
        String content = request.getParameter("content");

        System.out.println(nick);

        TradeBoard tradeBoard = TradeBoard.builder()
                .nick(nick)
                .classification(classification)
                .region(region)
                .title(title)
                .price(price)
                .content(content)
                .build();

        TradeBoard insertPost = tradeBoardService.postBoard(tradeBoard);
        model.addAttribute("newPost", insertPost);

        return "/member/tradeBoard";
    }
}
