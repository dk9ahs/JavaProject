package com.book.BookProject.member;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showRegisterForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());  // 빈 DTO 객체를 넘김
        return "member/Signup";  // Thymeleaf 템플릿 이름
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        try {
            // 폼에서 넘어온 값을 로그로 확인
            userService.registerUser(userDTO);  // UserDTO로 전달
            return "redirect:/login";  // 성공 시 로그인 페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/Signup";  // 에러 발생 시 다시 회원가입 페이지로
        }
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "member/Login";  // 로그인 페이지 템플릿 반환
    }

    // 아이디 중복 체크 API
    @PostMapping("/IdCheck")
    @ResponseBody
    public Map<String, Boolean> checkId(@RequestBody Map<String, String> requestData) {
        String id = requestData.get("id");
        boolean isUnique = userService.isIdUnique(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", !isUnique);  // 중복 여부에 따라 결과 설정
        return response;
    }

    // 닉네임 중복 체크 API
    @PostMapping("/NickCheck")
    @ResponseBody
    public Map<String, Boolean> checkNick(@RequestBody Map<String, String> requestData) {
        String nick = requestData.get("nick");
        boolean isUnique = userService.isNickUnique(nick);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", !isUnique);  // 중복 여부에 따라 결과 설정
        return response;
    }
}