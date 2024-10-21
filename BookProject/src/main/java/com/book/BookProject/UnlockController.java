package com.book.BookProject;

import com.book.BookProject.security.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UnlockController {

    private final UserServiceImpl userServiceImpl;

    public UnlockController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/guest/unlock")
    public String unlockUserAccount(@RequestParam("username") String username, RedirectAttributes redirectAttributes) {
        try {
            userServiceImpl.unlockAccount(username);  // 계정 잠금 해제
            redirectAttributes.addFlashAttribute("successMessage", "계정이 성공적으로 잠금 해제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "계정 잠금 해제에 실패했습니다. 관리자에게 문의하세요.");
        }
        return "redirect:/login";
    }
}