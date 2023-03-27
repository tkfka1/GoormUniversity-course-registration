package com.spring.api.response;

import com.spring.api.domain.Professor;
import lombok.Builder;
import lombok.Getter;

/**
 * 서비스 정책에 맞는 클래스
 */
@Getter
public class ProfessorResponse {

    private final Long id;
    private final String name;

    // 생성자 오버로딩
    public ProfessorResponse(Professor professor) {
        this.id = professor.getId();
        this.name = professor.getName();
    }


    @Builder
    public ProfessorResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
