package com.book.BookProject.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SignupController {

    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @GetMapping("/signup")
    public String showRegisterForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());  // 빈 DTO 객체를 넘김
        return "member/Signup";  // Thymeleaf 템플릿 이름
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userDTO") UserDTO userDTO, RedirectAttributes redirectAttributes) {
        try {
            signupService.registerUser(userDTO);
            // 회원가입 성공 후 Flash Attribute에 메시지 추가
            redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다.");
            return "redirect:/login";  // 로그인 페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            // 실패 시에도 Flash Attribute에 메시지 추가
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/signup";  // 회원가입 페이지로 리다이렉트
        }
    }


    // 아이디 중복 체크 API
    @PostMapping("/IdCheck")
    @ResponseBody
    public Map<String, Boolean> checkId(@RequestBody Map<String, String> requestData) {
        String id = requestData.get("id");
        boolean isUnique = signupService.isIdUnique(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", !isUnique);  // 중복 여부에 따라 결과 설정
        return response;
    }

    // 닉네임 중복 체크 API
    @PostMapping("/NickCheck")
    @ResponseBody
    public Map<String, Boolean> checkNick(@RequestBody Map<String, String> requestData) {
        String nick = requestData.get("nick");
        boolean isUnique = signupService.isNickUnique(nick);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", !isUnique);  // 중복 여부에 따라 결과 설정
        return response;
    }
}