package com.book.BookProject.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 일반 메일 발송 메서드
    public void sendMail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 발송 실패", e);
        }
    }

    // 인증번호 발송 메서드
    public String sendVerificationCode(String to) {
        String verificationCode = generateVerificationCode();
        String subject = "인증번호 발송";
        String text = "인증번호는 " + verificationCode + " 입니다.";
        sendMail(to, subject, text);
        return verificationCode;  // 생성된 인증번호 반환
    }

    // 임시 비밀번호 발송 메서드
    public String sendTempPassword(String to) {
        String tempPassword = generateTempPassword();
        String subject = "임시 비밀번호 발급";
        String text = "임시 비밀번호는 " + tempPassword + " 입니다.";
        sendMail(to, subject, text);
        return tempPassword;  // 생성된 임시 비밀번호 반환
    }

    // 인증번호 생성
    private String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 8);  // 8자리 인증번호 생성
    }

    // 임시 비밀번호 생성
    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 12);  // 12자리 임시 비밀번호 생성
    }
}