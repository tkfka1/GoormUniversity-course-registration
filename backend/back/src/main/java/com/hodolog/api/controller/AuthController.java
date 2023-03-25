package com.hodolog.api.controller;

import com.hodolog.api.config.AppConfig;
import com.hodolog.api.request.*;
import com.hodolog.api.response.SessionResponse;
import com.hodolog.api.response.UserResponse;
import com.hodolog.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        Long userId = authService.signin(login);

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        return new SessionResponse(jws, userId.intValue());
    }

    @PostMapping("/auth/signup")
    public void signup(@RequestBody Signup signup) {
        authService.signup(signup);
    }


    @GetMapping("/user/{id}")
    public UserResponse get(@PathVariable Long id) {
        return authService.get(id);
    }

    @GetMapping("/user")
    public List<UserResponse> getList(@ModelAttribute UserSearch userSearch) {
        return authService.getList(userSearch);
    }


    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable Long id) {
        authService.delete(id);
    }

    // 비밀번호 입력 있을 시
    @PutMapping("/user/{id}")
    public void edit(@PathVariable Long id, @RequestBody @Valid UserEdit request) {
        authService.edit(id, request);
    }

    // 비밀번호 입력 없을 때
    @PatchMapping("/user/{id}")
    public void edit2(@PathVariable Long id, @RequestBody @Valid UserEdit request) {
        authService.edit2(id, request);
    }
}
