//package com.book.BookProject;
//
//import com.book.BookProject.user.UserDTO;
//import com.book.BookProject.user.UserEntity;
//import com.book.BookProject.user.UserRepository;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class OAuth2LoginController {
//
//    private final HttpSession session;
//    private final UserRepository userRepository;
//
//    public OAuth2LoginController(HttpSession session, UserRepository userRepository) {
//        this.session = session;
//        this.userRepository = userRepository;
//    }
//
//    @GetMapping("/SocialLoginSuccess")
//    public String handleLoginRedirect() {
//        // 세션에서 리다이렉트할 URL 확인
//        String redirectUri = (String) session.getAttribute("redirectUri");
//
//        if (redirectUri != null) {
//            // 세션에서 redirectUri가 설정되어 있으면 해당 경로로 이동
//            return "redirect:" + redirectUri;
//        }
//
//        // 기본적으로 메인 페이지로 리다이렉트
//        return "redirect:/";
//    }
//
//    @GetMapping("/guest/SocialSignup")
//    public String showSocialSignupForm(Model model) {
//        UserEntity socialUser = (UserEntity) session.getAttribute("socialUser");
//
//        if (socialUser != null) {
//            UserDTO userDTO = new UserDTO();
//            userDTO.setEmail(socialUser.getEmail());
//            userDTO.setSocialProvider(socialUser.getSocialProvider());
//            userDTO.setSocialId(socialUser.getSocialId());
//            model.addAttribute("userDTO", userDTO);
//        } else {
//            model.addAttribute("userDTO", new UserDTO());
//        }
//
//        return "guest/SocialSignup";
//    }
//
//    @PostMapping("/guest/SocialSignup")
//    public String handleSocialSignup(@ModelAttribute UserDTO userDTO) {
//        // 세션에서 socialUser 정보를 가져오기
//        UserEntity socialUser = (UserEntity) session.getAttribute("socialUser");
//
//        if (socialUser != null) {
//            // 소셜 유저 정보 업데이트
//            socialUser.setNick(userDTO.getNick());
//            socialUser.setName(userDTO.getName());
//            socialUser.setPhone(userDTO.getPhone());
//            socialUser.setZipcode(userDTO.getZipcode());
//            socialUser.setAddress(userDTO.getAddress());
//            socialUser.setDetailAddress(userDTO.getDetailAddress());
//
//            // DB에 유저 정보 저장
//            userRepository.save(socialUser);
//
//            // 세션에서 socialUser 정보 제거
//            session.removeAttribute("socialUser");
//
//            // 메인 페이지로 리다이렉트
//            return "redirect:/";
//        }
//
//        // 오류 발생 시 다시 회원가입 페이지로
//        return "guest/SocialSignup";
//    }
//}