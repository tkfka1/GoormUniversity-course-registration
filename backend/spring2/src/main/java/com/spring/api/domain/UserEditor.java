package com.spring.api.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserEditor {

    private final String studentid;
    private final String name;
    private final String email;
    private final String password;



    @Builder
    public UserEditor(String studentid, String name, String email, String password) {
        this.studentid = studentid;
        this.name = name;
        this.email = email;
        this.password = password;

    }
}
