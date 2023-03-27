package com.spring.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class ProfessorEdit {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotNull(message = "전공을 입력해주세요.")
    private Long majorId;

    @Builder
    public ProfessorEdit(String name, Long majorId) {
        this.name = name;
        this.majorId = majorId;
    }

}
