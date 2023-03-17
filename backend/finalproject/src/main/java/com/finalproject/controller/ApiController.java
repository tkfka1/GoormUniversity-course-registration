package com.finalproject.controller;


import com.finalproject.domain.Data;
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
        gotoDBService.goDB(request);
    }

}
