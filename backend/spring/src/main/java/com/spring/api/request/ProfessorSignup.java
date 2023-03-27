package com.spring.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfessorSignup {
    private String name;
    private Long majorId;


}

