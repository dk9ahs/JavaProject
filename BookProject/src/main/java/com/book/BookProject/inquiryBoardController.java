package com.book.BookProject;

import com.book.BookProject.inquiryboard.InquiryBoard;
import com.book.BookProject.inquiryboard.InquiryBoardDTO;
import com.book.BookProject.inquiryboard.InquiryBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class inquiryBoardController {

    @Autowired
    InquiryBoardService inquiryBoardService;

    // 문의게시판 리스트
    @RequestMapping("/inquiryboard")
//    public  String inquiryBoard(Model model)
//    {
//        List<InquiryBoardDTO> list = inquiryBoardService.inquiryBoardList();
//        model.addAttribute("list", list);
//
//        return "/guest/inquiryBoard";
//    }
    public  String inquiryBoard(Model model, @PageableDefault(page = 0,size = 10,sort = "qidx",direction = Sort.Direction.DESC) Pageable pageable)
    {


        List<InquiryBoardDTO> list = inquiryBoardService.inquiryBoardList();
        model.addAttribute("list", list);

        return "/guest/inquiryBoard";
    }

    // 문의게시판 상세보기
    @RequestMapping("/inquiryboardview")
    public  String inquiryBoardView(Model model, Long qidx)
    {
        InquiryBoardDTO view = inquiryBoardService.inquiryBoardView(qidx);
        inquiryBoardService.inquiryBoardViewCount(qidx); // 제목 클릭 시 조회수 증가
        model.addAttribute("view", view);
        return "/member/inquiryBoardView";
    }

    // 문의게시판 글 작성 폼
    @RequestMapping("/inquiryboardwriteform")
    public  String inquiryBoardWriteForm()
    {
        return "/member/inquiryBoardWriteForm";
    }

    // 문의게시판 글 작성
    @RequestMapping("/inquiryboardwrite")
    public String inquiryBoardWrite(InquiryBoardDTO inquiryBoardDTO)
    {
        inquiryBoardService.inquiryBoardWrite(inquiryBoardDTO);

        return "redirect:/inquiryboard";
    }

    // 문의게시판 글 수정 폼
    @RequestMapping("/inquiryboardeditorform")
    public  String inquiryBoardEditorForm(Model model, Long qidx)
    {
        InquiryBoardDTO view = inquiryBoardService.inquiryBoardView(qidx);
        model.addAttribute("view", view);

        return "/member/inquiryBoardEditorForm";
    }

    // 문의게시판 글 수정
    @RequestMapping("/inquiryboardeditor")
    public  String inquiryBoardEditor(InquiryBoardDTO inquiryBoardDTO)
    {
        inquiryBoardService.inquiryBoardEditor(inquiryBoardDTO);

        return "redirect:/inquiryboardview" + inquiryBoardDTO.getQidx();
    }

    // 문의게시판 글 삭제
    @RequestMapping("/inquiryboarddelete")
    public  String inquiryBoardDelete(Long qidx)
    {
        inquiryBoardService.inquiryBoardDelete(qidx);

        return "redirect:/inquiryboard";
    }

}
