package com.finalproject.controller;


import com.finalproject.config.AppConfig;
import com.finalproject.config.data.UserSession;
import com.finalproject.domain.Data;
import com.finalproject.domain.Student;
import com.finalproject.excption.InvalidSigninInformation;
import com.finalproject.repository.UserRepository;
import com.finalproject.request.ApiCreate;
import com.finalproject.request.LoginCreate;
import com.finalproject.response.SessionResponse;
import com.finalproject.service.AuthService;
import com.finalproject.service.GotoDBService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.validation.Valid;
import java.security.Key;
import java.util.Base64;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiController {

    // Http Method
    // Get, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT

    private final GotoDBService gotoDBService;

    // GET
    // db 단건 조회
    @GetMapping("/get/{id}")
    public Data get(@PathVariable(name = "id") Long pid) {
        Data data = gotoDBService.outDB(pid);
        System.out.println("get조회");
        return data;
    }

    // POST
    @PostMapping("/post")
    public void post(@RequestBody @Valid ApiCreate request){
        // db.save(params)
        log.info(String.valueOf(request));
        gotoDBService.goDB(request);
    }


    // login post 요청

    private final AuthService authService;
    private final AppConfig appConfig;

//    @PostMapping("/login")
//    public SessionResponse login(@RequestBody LoginCreate request){
//        // db.save(params)
//        Long userId = authService.signin(request);
//
//// 키 만들기
////        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
////        byte[] encodedkey = key.getEncoded();
////        String strKey = Base64.getEncoder().encodeToString(encodedkey);
//////        Z4eO43yzASwbzAnntRz/BGhKlxClXShQ9zx/lW1qkZg=
////        System.out.println("key : " + strKey);
//
//        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());
//        log.info("key : " + key);
//        String jws = Jwts.builder()
//                .setSubject(String.valueOf(userId))
//                .signWith(key)
//                .compact();
//
//        return new SessionResponse(jws);
//
//    }


    @GetMapping("/gologin")
    public Long gologin(UserSession userSession){
        log.info("userSession : " + userSession.id);
        return userSession.id;
    }

//    @PostMapping("/signup")
//    public void signup(@RequestBody @Valid Student request){
//        log.info(String.valueOf(request));
//        authService.signup(request);
//    }

}
