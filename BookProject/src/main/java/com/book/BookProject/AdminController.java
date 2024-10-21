package com.book.BookProject;

import com.book.BookProject.user.UserEntity;
import com.book.BookProject.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 전체 회원 목록 조회
    @GetMapping("/list")
    public String getAllUsers(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10;  // 한 페이지에 표시할 회원 수
        Page<UserEntity> users = userRepository.findAll(PageRequest.of(page, pageSize));

        Map<String, Object> maps = new HashMap<>();
        maps.put("totalCount", users.getTotalElements());  // 총 회원 수
        maps.put("pageNum", page + 1);  // 현재 페이지 번호
        maps.put("pageSize", pageSize);  // 한 페이지에 보여줄 회원 수

        model.addAttribute("lists", users.getContent());
        model.addAttribute("maps", maps);  // maps 객체를 모델에 추가
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("currentPage", page);

        return "admin/UserList";
    }

    // 일반 회원 목록 조회
    @GetMapping("/localList")
    public String getLocalUsers(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10;
        Page<UserEntity> localUsers = userRepository.findBySocialProviderIsNull(PageRequest.of(page, pageSize));

        Map<String, Object> maps = new HashMap<>();
        maps.put("totalCount", localUsers.getTotalElements());  // 총 회원 수
        maps.put("pageNum", page + 1);  // 현재 페이지 번호
        maps.put("pageSize", pageSize);  // 한 페이지에 보여줄 회원 수

        model.addAttribute("lists", localUsers.getContent());
        model.addAttribute("maps", maps);  // maps 객체를 모델에 추가
        model.addAttribute("totalPages", localUsers.getTotalPages());
        model.addAttribute("currentPage", page);

        return "admin/UserList";
    }

    // 소셜 회원 목록 조회
    @GetMapping("/socialList")
    public String getSocialUsers(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10;
        Page<UserEntity> socialUsers = userRepository.findBySocialProviderIsNotNull(PageRequest.of(page, pageSize));

        Map<String, Object> maps = new HashMap<>();
        maps.put("totalCount", socialUsers.getTotalElements());  // 총 회원 수
        maps.put("pageNum", page + 1);  // 현재 페이지 번호
        maps.put("pageSize", pageSize);  // 한 페이지에 보여줄 회원 수

        model.addAttribute("lists", socialUsers.getContent());
        model.addAttribute("maps", maps);  // maps 객체를 모델에 추가
        model.addAttribute("totalPages", socialUsers.getTotalPages());
        model.addAttribute("currentPage", page);

        return "admin/UserList";
    }

    // 잠금 계정 회원 목록 조회
    @GetMapping("/lockList")
    public String getLockedUsers(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10;
        Page<UserEntity> lockedUsers = userRepository.findByAccountLocked(1, PageRequest.of(page, pageSize));

        Map<String, Object> maps = new HashMap<>();
        maps.put("totalCount", lockedUsers.getTotalElements());  // 총 회원 수
        maps.put("pageNum", page + 1);  // 현재 페이지 번호
        maps.put("pageSize", pageSize);  // 한 페이지에 보여줄 회원 수

        model.addAttribute("lists", lockedUsers.getContent());
        model.addAttribute("maps", maps);  // maps 객체를 모델에 추가
        model.addAttribute("totalPages", lockedUsers.getTotalPages());
        model.addAttribute("currentPage", page);

        return "admin/UserList";
    }
}