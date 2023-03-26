package com.spring.api.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserEditor {

    private final String studentId;
    private final String name;
    private final String password;

    private final String email;
    private final Long majorId;
    private final Long credit;



    @Builder
    public UserEditor(String studentId, String name, String password, String email, Long majorId, Long credit) {
        this.studentId = studentId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.majorId = majorId;
        this.credit = credit;

    }
}
