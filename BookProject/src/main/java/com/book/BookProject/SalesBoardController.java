package com.book.BookProject;

import com.book.BookProject.salesboard.SalesBoardDTO;
import com.book.BookProject.salesboard.SalesBoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/salesboard")
@RequiredArgsConstructor
public class SalesBoardController {

    @Autowired
    SalesBoardService salesBoardService;
    @Autowired
    ServletContext context;

    // 게시판 목록 조회
    @GetMapping
    public String list(Model model){
        List<SalesBoardDTO> salesBoardDTOS = salesBoardService.getAllSalesBoards();
        model.addAttribute("salesBoards", salesBoardDTOS);

        return "/guest/salesboardlist";
    }

    // 글쓰기 폼 이동
    @GetMapping("/write")
    public String createForm() {
        return "member/salesboardwriteform";
    }

    // 글쓰기 처리
    @PostMapping("/write")
    public String create(HttpServletRequest request, SalesBoardDTO  salesBoardDTO,  @RequestParam("file") MultipartFile file) throws FileNotFoundException {

        if (file != null && !file.isEmpty()) {
            String oImageName = file.getOriginalFilename();
            String uploadDir = request.getSession().getServletContext().getRealPath("/"); // webapp에 저장되도록

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }

            String sImageName = UUID.randomUUID().toString() + "_" + oImageName;

            File destination = new File(dir, sImageName);
            try {
                file.transferTo(destination);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/salesboard/write?status=fail";
            }

            // 파일이 있을 경우에만 DTO에 이미지 설정
            salesBoardDTO.setOimage(oImageName);
            salesBoardDTO.setSimage(sImageName);
        } else {
            // 파일이 없을 때 처리할 내용 (필요한 경우)
            System.out.println("No file uploaded.");
        }

        // 지역 데이터
        String sido = request.getParameter("sido1");
        String gugun = request.getParameter("gugun1");
        salesBoardDTO.setRegion(sido + " " + gugun);
        // 가격 데이터 default 값 넣기
        Integer price = (salesBoardDTO.getPrice() == null) ? 0 : salesBoardDTO.getPrice();
        salesBoardDTO.setPrice(price);

        salesBoardService.createSalesBoard(salesBoardDTO);
        return "redirect:/salesboard";
    }

    // 게시글 상세 보기
    @GetMapping("/detail")
    public String detail(Long sidx, Model model) {
        model.addAttribute("salesBoard", salesBoardService.getSalesBoardById(sidx));

        return "/guest/salesboarddetail";
    }

    // 게시글 삭제 하기
    @GetMapping("/delete")
    public String delete(Long sidx){
        salesBoardService.deletedSalesBored(sidx);

        return "redirect:/salesboard";
    }

    // 게시글 수정 폼
    @GetMapping("/edit")
    public String editForm(Long sidx, Model model) {
        model.addAttribute("salesBoard", salesBoardService.getSalesBoardById(sidx));

        return "member/salesboardeditorform";
    }

    // 글쓰기 수정 처리
    @PostMapping("/edit")
    public String edit(HttpServletRequest request, SalesBoardDTO  salesBoardDTO,  @RequestParam("file") MultipartFile file) throws FileNotFoundException {
        
        // 파일 업로드
        if (file != null && !file.isEmpty()) {
            String oImageName = file.getOriginalFilename();
            String uploadDir = request.getSession().getServletContext().getRealPath("/"); // webapp에 저장되도록

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }

            String sImageName = UUID.randomUUID().toString() + "_" + oImageName;

            File destination = new File(dir, sImageName);
            try {
                file.transferTo(destination);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/salesboard/write?status=fail";
            }

            // 파일이 있을 경우에만 DTO에 이미지 설정
            salesBoardDTO.setOimage(oImageName);
            salesBoardDTO.setSimage(sImageName);
        } else {
            // 파일이 없을 때
            System.out.println("No file uploaded.");
        }

        // 지역 데이터
        String sido = request.getParameter("sido1");
        String gugun = request.getParameter("gugun1");
        salesBoardDTO.setRegion(sido + " " + gugun);

        // 가격 데이터 default 값 넣기
        Integer price = (salesBoardDTO.getPrice() == null) ? 0 : salesBoardDTO.getPrice();
        salesBoardDTO.setPrice(price);

        salesBoardService.updateSalesBored(salesBoardDTO.getSidx(), salesBoardDTO);
        return "redirect:/salesboard/detail?sidx=" + salesBoardDTO.getSidx();
    }

//    // 게시글 수정 폼 이동
//    @GetMapping("/edit/{sIdx}")
//    public String editForm(@PathVariable Long sIdx, Model model) {
//        model.addAttribute("salesBoard", salesBoardService.getSalesBoardById(sIdx));
//        return "salesboard/form";  // 수정 폼
//    }
//
//    // 게시글 수정 처리
//    @PostMapping("/edit/{sIdx}")
//    public String update(@PathVariable Long sIdx, @ModelAttribute SalesBoardDTO salesBoardDTO) {
//        salesBoardService.updateSalesBoard(sIdx, salesBoardDTO);
//        return "redirect:/salesboard";
//    }
}