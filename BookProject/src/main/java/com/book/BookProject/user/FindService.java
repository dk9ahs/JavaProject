package com.book.BookProject.user;

public interface FindService {

    String findIdByNameAndPhone(String name, String phone);  // 전화번호로 아이디 찾기 추가

    void sendTempPassword(String email, String name);
}