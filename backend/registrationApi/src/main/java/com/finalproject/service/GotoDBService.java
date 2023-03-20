package com.finalproject.service;

import com.finalproject.domain.Data;
import com.finalproject.repository.DataRepository;
import com.finalproject.request.ApiCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GotoDBService {

    @Autowired
    private final DataRepository dataRepository;

    public void goDB(ApiCreate apiCreate){

        Data data = Data.builder()
                .title(apiCreate.getTitle())
                .content(apiCreate.getContent())
                .build();

        dataRepository.save(data);
    }


    public Data outDB(Long id){

        Data data = dataRepository.findById(id)
            .orElseThrow(() ->new IllegalArgumentException("존재하지 않는 글입니다."));

        return data;
    }
}
