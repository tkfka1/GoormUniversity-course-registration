package com.spring.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class UserEdit {

    @NotBlank(message = "교원번호를 입력하세요.")
    private String studentId;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "수강학점을 입력해주세요.")
    private Long credit;
    @NotBlank(message = "전공을 입력해주세요.")
    private Long majorId;

    @Builder
    public UserEdit(String studentId, String name, String password, String email, Long credit, Long majorId) {
        this.studentId = studentId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.credit = credit;
        this.majorId = majorId;
    }


}
