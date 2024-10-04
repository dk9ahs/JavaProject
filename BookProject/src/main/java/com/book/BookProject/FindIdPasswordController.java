package com.book.BookProject;

import com.book.BookProject.user.FindService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/find")
public class FindIdPasswordController {

    private final FindService findService;

    public FindIdPasswordController(FindService findService) {
        this.findService = findService;
    }

    // 아이디/비밀번호 찾기 폼 페이지 통합
    @GetMapping
    public String showFindIdPasswordForm() {
        return "guest/FindIdPwd"; // 아이디 및 비밀번호 찾기 통합 페이지
    }

    // 아이디 찾기 요청 처리 (전화번호로)
    @PostMapping("/id")
    @ResponseBody
    public Map<String, Object> findId(@RequestBody Map<String, String> requestData) {
        String name = requestData.get("name");
        String phone = requestData.get("phone");

        Map<String, Object> response = new HashMap<>();
        try {
            String foundId = findService.findIdByNameAndPhone(name, phone);
            response.put("foundId", foundId); // 찾은 아이디를 반환
        } catch (IllegalArgumentException e) {
            response.put("foundId", null); // 일치하는 정보를 찾을 수 없음
        }

        return response; // JSON 형식으로 응답
    }

    // 비밀번호 찾기 요청 처리 (이메일로)
    @PostMapping("/password")
    public String findPassword(@RequestParam("email") String email,
                               @RequestParam("name") String name,
                               RedirectAttributes redirectAttributes) {
        try {
            findService.sendTempPassword(email, name); // 임시 비밀번호 전송
            redirectAttributes.addFlashAttribute("successMessage", "임시 비밀번호가 이메일로 발송되었습니다.");
            return "redirect:/login"; // 성공 시 로그인 페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            // 비밀번호를 찾을 수 없는 경우
            redirectAttributes.addFlashAttribute("errorMessage", "일치하는 정보를 찾을 수 없습니다.");
            return "redirect:/find"; // 오류 메시지를 가지고 페이지 리다이렉트
        }
    }
}
