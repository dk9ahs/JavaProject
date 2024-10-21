package com.book.BookProject;

import com.book.BookProject.user.UnlockService;
import com.book.BookProject.user.UserEntity;
import com.book.BookProject.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
@RequestMapping("/guest")
public class UnlockController {

    private final UnlockService unlockService;
    private final UserRepository userRepository;

    @Autowired
    public UnlockController(UnlockService unlockService, UserRepository userRepository) {
        this.unlockService = unlockService;
        this.userRepository = userRepository;
    }

    // 계정 잠금 해제 페이지
    @GetMapping("/unlock")
    public String showUnlockPage() {
        return "guest/UnlockUser"; // UnlockUser.html 페이지 반환
    }

    // 인증번호 발송 처리
    @PostMapping("/sendUnlockCode")
    public String sendUnlockVerificationCode(@RequestParam String email, Model model) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            unlockService.sendUnlockVerificationCode(email);  // 이메일로 인증번호 발송
            model.addAttribute("successMessage", "인증번호가 이메일로 발송되었습니다.");
        } else {
            model.addAttribute("errorMessage", "해당 이메일로 등록된 계정이 없습니다.");
        }
        return "guest/UnlockUser";
    }

    // 인증번호 확인 및 잠금 해제 처리
    @PostMapping("/unlock")
    public String unlockAccount(@RequestParam String email, @RequestParam String authCode, Model model) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (unlockService.verifyUnlockAuthCode(authCode)) {
                unlockService.unlockAccount(user);  // 계정 잠금 해제
                unlockService.generateTempPasswordAndSendEmail(user.getEmail());  // 임시 비밀번호 생성 및 전송
                model.addAttribute("successMessage", "계정 잠금이 해제되었습니다. 임시 비밀번호가 이메일로 발송되었습니다.");
            } else {
                model.addAttribute("errorMessage", "인증번호가 일치하지 않습니다.");
            }
        } else {
            model.addAttribute("errorMessage", "해당 이메일로 사용자를 찾을 수 없습니다.");
        }
        return "guest/UnlockUser";
    }
}