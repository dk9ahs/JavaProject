package com.book.BookProject;

import com.book.BookProject.inquiryboard.InquiryBoardDTO;
import com.book.BookProject.inquiryboard.InquiryBoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("/inquiryboard")
@Controller
public class InquiryBoardController
{
    @Autowired
    InquiryBoardService inquiryBoardService;
    @Autowired
    ServletContext context;

    // 문의게시판 리스트
    @GetMapping
    public  String inquiryBoard(Model model,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(required = false) String searchField,
                                @RequestParam(required = false) String searchWord)
    {
        Page<InquiryBoardDTO> listPage = inquiryBoardService.inquiryBoardList(page - 1, searchField, searchWord);

        long totalCount = listPage.getTotalElements();
        int totalPage = listPage.getTotalPages();
        int currentGroup = (page - 1) / 5; // 현재 그룹 (0부터 시작)
        int pageSize = listPage.getSize();
        // 리스트
        model.addAttribute("list", listPage.getContent());
        // 페이지
        model.addAttribute("totalPage", totalPage); // 총 페이지
        model.addAttribute("currentPage", page); // 현재 페이지 추가
        model.addAttribute("currentGroup", currentGroup);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", pageSize);
        // 검색
        model.addAttribute("searchField", searchField); // 검색필드
        model.addAttribute("searchWord", searchWord); // 검색어

        return "guest/InquiryBoard";
    }

    // 문의게시판 상세보기
    @GetMapping("/view")
    public  String inquiryBoardView(Model model, Long qidx)
    {
        inquiryBoardService.inquiryBoardViewCount(qidx); // 제목 클릭 시 조회수 증가

        InquiryBoardDTO view = inquiryBoardService.inquiryBoardView(qidx);

        model.addAttribute("view", view);

        return "/member/InquiryBoardView";
    }

    // 문의게시판 글 작성 폼
    @GetMapping("/writeform")
    public  String inquiryBoardWriteForm()
    {
        return "member/InquiryBoardWriteForm";
    }

    // 문의게시판 글 작성
    @PostMapping("/write")
    public String inquiryBoardWrite(HttpServletRequest request, InquiryBoardDTO inquiryBoardDTO, @RequestParam("file") MultipartFile file) throws FileNotFoundException
    {
        // 파일 업로드
        if(file != null && !file.isEmpty())
        {
            String oFileName = file.getOriginalFilename();
            String uploadDir = request.getSession().getServletContext().getRealPath("/"); // src/main/webapp에 저장

            File dir = new File(uploadDir);
            if(!dir.exists())
            {
                dir.mkdir();
            }

            String sFileName = UUID.randomUUID().toString() + "_" + oFileName;

            File destination = new File(dir, sFileName);
            try {
                file.transferTo(destination);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/inquirvboard/write?status=fail";
            }

            // 파일 있을 경우에만 DTO에 이미지 설정
            inquiryBoardDTO.setOfile(oFileName);
            inquiryBoardDTO.setSfile(sFileName);
        } else
        {
            // 파일이 없을 떄
            System.out.println("No file uploaded.");
        }
        System.out.println(inquiryBoardDTO.getOfile());
        System.out.println(inquiryBoardDTO.getSfile());

        inquiryBoardService.inquiryBoardWrite(inquiryBoardDTO);

        return "redirect:/inquiryboard";
    }

    // 문의게시판 글 수정 폼
    @GetMapping("/editform")
    public  String inquiryBoardEditorForm(Model model, Long qidx)
    {
        InquiryBoardDTO inquiryBoard = inquiryBoardService.inquiryBoardView(qidx);
        model.addAttribute("inquiryBoard", inquiryBoard);

        return "/member/InquiryBoardEditorForm";
    }

    // 문의게시판 글 수정
    @PostMapping("/edit")
    public  String inquiryBoardEditor(HttpServletRequest request, InquiryBoardDTO inquiryBoardDTO, @RequestParam("file") MultipartFile file) throws FileNotFoundException
    {
        // 파일 업로드
        if(file != null && !file.isEmpty())
        {
            String oFileName = file.getOriginalFilename();
            String uploadDir = request.getSession().getServletContext().getRealPath("/"); // src/main/webapp에 저장

            File dir = new File(uploadDir);
            if(!dir.exists())
            {
                dir.mkdir();
            }

            String sFileName = UUID.randomUUID().toString() + "_" + oFileName;

            File destination = new File(dir, sFileName);
            try {
                file.transferTo(destination);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/inquirvboard/write?status=fail";
            }

            // 파일 있을 경우에만 DTO에 이미지 설정
            inquiryBoardDTO.setOfile(oFileName);
            inquiryBoardDTO.setSfile(sFileName);
        } else
        {
            // 파일이 없을 떄
            System.out.println("No file uploaded.");
        }
        inquiryBoardService.inquiryBoardEditor(inquiryBoardDTO.getQidx(), inquiryBoardDTO);

        return "redirect:/inquiryboard/view?qidx=" + inquiryBoardDTO.getQidx();
    }

    // 문의게시판 글 삭제
    @GetMapping("/delete")
    public  String inquiryBoardDelete(Long qidx)
    {
        inquiryBoardService.inquiryBoardDelete(qidx);

        return "redirect:/inquiryboard";
    }

    // 문의게시판 답글 작성 폼
    @GetMapping("/replywriteform")
    public  String inquiryBoardReplyWriteForm(Model model, Long qidx)
    {
        InquiryBoardDTO inquiryBoardDTO = inquiryBoardService.inquiryBoardView(qidx);
        model.addAttribute("inquiryBoardDTO", inquiryBoardDTO);

        return "admin/InquiryBoardReplyWriteForm";
    }

    // 문의게시판 답글 작성
    @PostMapping("/replywrite")
    public  String inquiryBoardReplyWrite(HttpServletRequest request,
                                          InquiryBoardDTO inquiryBoardDTO,
                                          @RequestParam("file") MultipartFile file) throws FileNotFoundException
    {
        // 파일 업로드
        if(file != null && !file.isEmpty())
        {
            String oFileName = file.getOriginalFilename();
            String uploadDir = request.getSession().getServletContext().getRealPath("/"); // src/main/webapp에 저장

            File dir = new File(uploadDir);
            if(!dir.exists())
            {
                dir.mkdir();
            }

            String sFileName = UUID.randomUUID().toString() + "_" + oFileName;

            File destination = new File(dir, sFileName);
            try {
                file.transferTo(destination);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/inquirvboard/replywrite?status=fail";
            }

            // 파일 있을 경우에만 DTO에 이미지 설정
            inquiryBoardDTO.setOfile(oFileName);
            inquiryBoardDTO.setSfile(sFileName);
        } else
        {
            // 파일이 없을 떄
            System.out.println("No file uploaded.");
        }
        System.out.println(inquiryBoardDTO.getOfile());
        System.out.println(inquiryBoardDTO.getSfile());

        inquiryBoardService.inquiryBoardReplyWrite(inquiryBoardDTO);

        return "redirect:/inquiryboard";
    }
}
