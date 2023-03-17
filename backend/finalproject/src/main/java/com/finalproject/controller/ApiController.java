package com.finalproject.controller;


import com.finalproject.request.ApiCreate;
import com.finalproject.service.GotoDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiController {

    // Http Method
    // Get, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT

    // GET
    // db 단건 조회
    @GetMapping("/get")
    public void get(@PathVariable(name = "id") Long pid) {
//        GotoDBService.outDB(pid);

    }

    private final GotoDBService gotoDBService;

    // POST
    @PostMapping("/post")
    public void post(@RequestBody @Valid ApiCreate request){
        // db.save(params)
        gotoDBService.goDB(request);
    }

}
