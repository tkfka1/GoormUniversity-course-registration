package com.finalproject.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/auth/login")
    public void login(){
        // json 아이디/ 비밀번호
        // DB에서 조회
        // 토큰을 할당
    
    }
}
