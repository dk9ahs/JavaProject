package com.book.BookProject;

import com.book.BookProject.user.OAuth2Service;
import com.book.BookProject.user.RecaptchaService;
import com.book.BookProject.user.SignupService;
import com.book.BookProject.user.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SignupController {

    private final SignupService signupService;
    private final RecaptchaService recaptchaService;  // RecaptchaService 의존성 주입
    private final OAuth2Service oAuth2Service;



    public SignupController(SignupService signupService,
                            RecaptchaService recaptchaService,
                            OAuth2Service oAuth2Service)
    {
        this.signupService = signupService;
        this.recaptchaService = recaptchaService;
        this.oAuth2Service = oAuth2Service;
    }


    @GetMapping("/signup")
    public String showRegisterForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());  // 빈 DTO 객체를 넘김
        return "guest/Signup";  // Thymeleaf 템플릿 이름
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userDTO") UserDTO userDTO,
                           @RequestParam("g-recaptcha-response") String recaptchaResponse,
                           RedirectAttributes redirectAttributes) {

        // 서버에서 reCAPTCHA 검증 실행
        if (!recaptchaService.verifyRecaptcha(recaptchaResponse)) {
            redirectAttributes.addFlashAttribute("errorMessage", "reCAPTCHA 인증에 실패했습니다.");
            return "redirect:/signup";  // 실패 시 다시 회원가입 페이지로 리다이렉트
        }
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

    @GetMapping("/socialSignupCheck")
    public String socialSignupCheck(Model model) {
        UserDTO currentUser = oAuth2Service.getCurrentUser();  // OAuth2Service 사용하여 소셜 로그인 후 사용자 정보 가져오기

        if (currentUser == null) {
            model.addAttribute("userDTO", new UserDTO());
            return "guest/Socialsignup";  // 추가 정보 입력 페이지로 이동
        }

        return "redirect:/";  // 이미 회원 정보가 있을 경우 메인 페이지로 이동
    }

    @PostMapping("/socialRegister")
    public String socialRegister(@ModelAttribute("userDTO") UserDTO userDTO,
                                 RedirectAttributes redirectAttributes) {
        try {
            signupService.registerSocialUser(userDTO);  // 소셜 로그인 사용자를 DB에 저장
            redirectAttributes.addFlashAttribute("successMessage", "소셜 회원가입이 완료되었습니다.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/socialSignupCheck";
        }
    }

    @PostMapping("/IdCheck")  // POST 메서드 사용
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