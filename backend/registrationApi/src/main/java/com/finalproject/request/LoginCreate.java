package com.finalproject.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
public class LoginCreate {
    @NotBlank(message = "id 값이 입력이 안됨")
    private String STUDENT_id;
    @NotBlank(message = "pw 값이 입력이 안됨")
    private String STUDENT_pw;

    @Builder
    public LoginCreate(String STUDENT_id,String STUDENT_pw){
        this.STUDENT_id = STUDENT_id;
        this.STUDENT_pw = STUDENT_pw;
    }

}
