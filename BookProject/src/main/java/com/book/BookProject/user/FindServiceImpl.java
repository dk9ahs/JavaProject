package com.book.BookProject.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FindServiceImpl implements FindService{
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public FindServiceImpl(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    // 이름과 전화번호로 아이디 찾기 메서드 추가
    @Override
    public String findIdByNameAndPhone(String name, String phone) {
        Optional<UserEntity> userOptional = userRepository.findByNameAndPhone(name, phone);
        if (userOptional.isPresent()) {
            return userOptional.get().getId();
        } else {
            throw new IllegalArgumentException("해당하는 사용자가 없습니다.");
        }
    }

    @Override
    public void sendTempPassword(String email, String name) {
        Optional<UserEntity> userOptional = userRepository.findByNameAndEmail(name, email);
        if (userOptional.isPresent()) {
            String tempPassword = generateTempPassword();
            UserEntity user = userOptional.get();
            user.setPwd(tempPassword);
            userRepository.save(user);
            sendEmail(email, "임시 비밀번호 발송", "임시 비밀번호는 " + tempPassword + " 입니다.");
        } else {
            throw new IllegalArgumentException("해당하는 사용자가 없습니다.");
        }
    }

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);  // 임시 비밀번호 생성
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 발송 중 오류가 발생했습니다.");
        }
    }
}