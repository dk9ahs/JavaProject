package com.book.BookProject;

import com.book.BookProject.user.ProfileUpdateService;
import com.book.BookProject.user.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/member")
public class ProfileUpdateController {

    private final ProfileUpdateService profileUpdateService;

    public ProfileUpdateController(ProfileUpdateService profileUpdateService) {
        this.profileUpdateService = profileUpdateService;
    }

    @GetMapping("Update")
    public String showProfileUpdateForm(Model model, Principal principal) {
        // 현재 사용자의 정보를 가져와서 모델에 추가
        UserDTO userDTO = profileUpdateService.getUserByUsername(principal.getName());
        model.addAttribute("userDTO", userDTO);
        return "member/profileUpdate"; // 해당하는 Thymeleaf 페이지로 연결
    }

    @PostMapping("Update")
    public String updateProfile(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result,
                                Model model, Principal principal,
                                HttpServletRequest request, HttpServletResponse response) {
        if (result.hasErrors()) {
            return "member/profileUpdate"; // 유효성 검사에 실패한 경우 다시 폼으로
        }

        try {
            // 사용자 정보 업데이트 처리
            profileUpdateService.updateUserProfile(userDTO, principal.getName());

            // 세션 무효화 및 로그아웃 처리
            request.getSession().invalidate(); // 세션 무효화
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

            model.addAttribute("successMessage", "회원정보가 성공적으로 수정되었습니다.");
            return "redirect:/login?logout"; // 수정 후 로그인 페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());  // 오류 메시지 출력
            return "member/profileUpdate"; // 오류 발생 시 다시 폼으로
        } catch (Exception e) {
            model.addAttribute("errorMessage", "오류가 발생했습니다: " + e.getMessage());
            return "member/profileUpdate";
        }
    }
    @PostMapping("/withdraw")
    public String withdrawMember(Principal principal, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        // 회원 정보 삭제 처리
        profileUpdateService.deleteUserById(principal.getName());

        // 세션 무효화 및 로그아웃 처리
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        // 탈퇴 성공 메시지 전달
        redirectAttributes.addFlashAttribute("withdrawSuccess", "회원탈퇴가 완료되었습니다.");

        // 로그아웃 후 로그인 페이지로 리다이렉트
        return "redirect:/login?logout=true";
    }

}