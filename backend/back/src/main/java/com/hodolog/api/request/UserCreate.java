package com.hodolog.api.request;

import com.hodolog.api.exception.InvalidRequest;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Setter
@Getter
@ToString
public class UserCreate {

    private String studentid;
    private String email;
    private String password;
    private String name;

//    public void validate() {
//        if (title.contains("바보")) {
//            throw new InvalidRequest("title", "제목에 바보를 포함할 수 없습니다.");
//        }
//    }
}
