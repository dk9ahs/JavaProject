//package com.book.BookProject;
//
//import com.book.BookProject.oauth2.OAuth2Service;
//import com.book.BookProject.user.UserDTO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//public class OAuth2LoginController {
//
//
//    private static final Logger logger = LoggerFactory.getLogger(OAuth2LoginController.class);
//
//    private final OAuth2Service oAuth2Service;
//    private final OAuth2SignupService oAuth2SignupService;
//
//    public OAuth2LoginController(OAuth2Service oAuth2Service, OAuth2SignupService oAuth2SignupService) {
//        this.oAuth2Service = oAuth2Service;
//        this.oAuth2SignupService = oAuth2SignupService;
//    }
//
//    // 소셜 로그인 후 추가 정보 입력 확인
//    @GetMapping("/socialSignupCheck")
//    public String socialSignupCheck(Model model) {
//        try {
//            UserDTO currentUser = oAuth2Service.getCurrentUser();
//
//            // DB에 해당 소셜 사용자가 없으면 회원가입 페이지로 리다이렉트
//            if (currentUser == null || currentUser.getNick() == null) {
//                logger.info("소셜 사용자가 DB에 없습니다. 회원가입 페이지로 리다이렉트합니다.");
//                return "guest/SocialSignup";  // 소셜 회원가입 페이지로 이동
//            }
//
//            // 소셜 사용자 정보가 이미 DB에 있으면 메인 페이지로 리다이렉트
//            logger.info("소셜 사용자 정보가 DB에 있습니다. 메인 페이지로 리다이렉트합니다.");
//            return "redirect:/";
//        } catch (Exception e) {
//            // 예외가 발생한 경우 로그에 기록하고, 에러 페이지로 리다이렉트
//            logger.error("소셜 로그인 확인 중 오류가 발생했습니다: ", e);
//            model.addAttribute("errorMessage", "소셜 로그인 중 오류가 발생했습니다. 다시 시도해 주세요.");
//            return "error/errorPage";  // 에러 페이지로 리다이렉트
//        }
//    }
//
//    // 소셜 회원가입 페이지 렌더링
//    @GetMapping("/socialSignup")
//    public String showSocialSignupForm(Model model) {
//        UserDTO currentUser = oAuth2Service.getCurrentUser();  // 소셜 로그인 사용자 정보 가져오기
//
//        if (currentUser == null || currentUser.getNick() == null) {
//            model.addAttribute("userDTO", new UserDTO());  // 소셜 사용자 정보가 없으면 빈 DTO 전달
//            return "guest/SocialSignup";  // 소셜 회원가입 페이지로 이동
//        }
//
//        return "redirect:/";  // 이미 소셜 회원이면 메인 페이지로 리다이렉트
//    }
//
//    // 소셜 회원가입 처리 (POST 요청 처리)
//    @PostMapping("/socialSignup")
//    public String registerSocialUser(@ModelAttribute("userDTO") UserDTO userDTO, RedirectAttributes redirectAttributes) {
//        try {
//            // 소셜 회원가입 처리 로직
//            oAuth2SignupService.registerSocialUser(userDTO);
//            redirectAttributes.addFlashAttribute("successMessage", "소셜 회원가입이 완료되었습니다.");
//            return "redirect:/";  // 회원가입 성공 후 메인 페이지로 리다이렉트
//        } catch (IllegalArgumentException e) {
//            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
//            return "redirect:/SocialSignup";  // 회원가입 실패 시 다시 회원가입 페이지로 리다이렉트
//        }
//    }
//}