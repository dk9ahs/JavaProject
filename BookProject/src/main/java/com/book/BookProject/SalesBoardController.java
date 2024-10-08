package com.book.BookProject;

import com.book.BookProject.sales.SalesBoardDTO;
import com.book.BookProject.sales.SalesBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/salesboard")
@RequiredArgsConstructor
public class SalesBoardController {

    private final SalesBoardService salesBoardService;

    // 게시판 목록 조회
    @GetMapping
    public String list(Model model) {
        model.addAttribute("salesBoards", salesBoardService.getAllSalesBoards());
        return "/guest/salesboardlist";
    }

    // 게시글 상세 보기
    @GetMapping("/detail/{sIdx}")
    public String detail(@PathVariable Long sIdx, Model model) {
        model.addAttribute("salesBoard", salesBoardService.getSalesBoardById(sIdx));
        return "salesboard/detail";  // detail.html 파일로 이동
    }

    // 글쓰기 폼 이동
    @GetMapping("/write")
    public String createForm() {
        return "member/salesboardWriteForm";  // form.html 파일로 이동
    }

    // 글쓰기 처리
    @PostMapping("/write")
    public String create(@ModelAttribute SalesBoardDTO salesBoardDTO) {
        salesBoardService.createSalesBoard(salesBoardDTO);
        return "redirect:/salesboard";
    }

    // 게시글 삭제
    @PostMapping("/delete/{sIdx}")
    public String delete(@PathVariable Long sIdx, @RequestParam String currentNick) {
        salesBoardService.deleteSalesBoard(sIdx, currentNick);
        return "redirect:/salesboard";
    }

    // 게시글 수정 폼 이동
    @GetMapping("/edit/{sIdx}")
    public String editForm(@PathVariable Long sIdx, Model model) {
        model.addAttribute("salesBoard", salesBoardService.getSalesBoardById(sIdx));
        return "salesboard/form";  // 수정 폼
    }

    // 게시글 수정 처리
    @PostMapping("/edit/{sIdx}")
    public String update(@PathVariable Long sIdx, @ModelAttribute SalesBoardDTO salesBoardDTO) {
        salesBoardService.updateSalesBoard(sIdx, salesBoardDTO);
        return "redirect:/salesboard";
    }
}