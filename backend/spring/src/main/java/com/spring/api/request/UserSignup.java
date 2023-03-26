package com.spring.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignup {

    private String studentId;
    private String name;
    private String password;
    private String email;
    private Long credit;
    private Long majorId;
}
