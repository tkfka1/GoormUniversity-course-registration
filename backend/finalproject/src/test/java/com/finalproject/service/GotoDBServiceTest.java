package com.finalproject.service;

import com.finalproject.domain.Data;
import com.finalproject.repository.DataRepository;

import com.finalproject.request.ApiCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GotoDBServiceTest {

    @Autowired
    private GotoDBService gotoDBService;
    @Autowired
    private DataRepository dataRepository;

    @Test
    @DisplayName("db 테스트 하기")
    void test1(){
        //given
        Long pid = 1L;

        // when
        Data data = gotoDBService.outDB(pid);

        // then
        assertNotNull(data);
        System.out.println(data);
        System.out.println(data);
        System.out.println(data);
        System.out.println(data);
        System.out.println(data);
        System.out.println(data);
        System.out.println(data);




    }
}