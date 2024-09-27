package com.book.BookProject;

import com.book.BookProject.member.UserEntity;
import com.book.BookProject.member.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserEntity userEntity) {
        if (userService.checkEmailDuplication(userEntity.getEmail())) {
            return ResponseEntity.badRequest().body("이메일이 이미 존재합니다.");
        }
        if (userService.checkNickDuplication(userEntity.getNick())) {
            return ResponseEntity.badRequest().body("닉네임이 이미 존재합니다.");
        }

        userService.registerUser(userEntity);
        return ResponseEntity.ok("회원가입 성공!");
    }

    // 닉네임 중복 확인
    @GetMapping("/checkNick")
    public ResponseEntity<Boolean> checkNickDuplication(@RequestParam String nick) {
        return ResponseEntity.ok(userService.checkNickDuplication(nick));
    }

    // 이메일 중복 확인
    @GetMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmailDuplication(@RequestParam String email) {
        return ResponseEntity.ok(userService.checkEmailDuplication(email));
    }
}
