package com.finalproject.controller;


import com.finalproject.domain.Data;
import com.finalproject.domain.Student;
import com.finalproject.excption.InvalidSigninInformation;
import com.finalproject.repository.UserRepository;
import com.finalproject.request.ApiCreate;
import com.finalproject.request.LoginCreate;
import com.finalproject.service.GotoDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    private final UserRepository userRepository;
    @PostMapping("/login")
    public Student login(@RequestBody @Valid LoginCreate request){
        // db.save(params)
        log.info(String.valueOf(request));

        // db 조회
        Student student = userRepository.findByStudentidAndPw(request.getSTUDENT_id(), request.getSTUDENT_pw())
                .orElseThrow(InvalidSigninInformation::new);

        // 토큰을 응답
        log.info("{}", student);

        return student;

    }

}
