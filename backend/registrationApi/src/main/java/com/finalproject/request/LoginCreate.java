package com.finalproject.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Setter
@Getter
public class LoginCreate {

    @NotBlank(message = "id 값이 입력이 안됨")
    private String loginid;
    @NotBlank(message = "pw 값이 입력이 안됨")
    private String loginpw;

    @Builder
    public LoginCreate(String loginid, String loginpw){
        this.loginid = loginid;
        this.loginpw = loginpw;
    }

}
