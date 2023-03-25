package com.hodolog.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class UserEdit {

    @NotBlank(message = "학번을 입력하세요.")
    private String studentid;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    public UserEdit(String studentid, String name, String email, String password) {
        this.studentid = studentid;
        this.name = name;
        this.email = email;
        this.password = password;
    }


}
