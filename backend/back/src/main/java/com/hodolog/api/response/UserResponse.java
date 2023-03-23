package com.hodolog.api.response;

import com.hodolog.api.domain.User;
import lombok.Builder;
import lombok.Getter;

/**
 * 서비스 정책에 맞는 클래스
 */
@Getter
public class UserResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String studentid;

    // 생성자 오버로딩
    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.studentid = user.getStudentid();
    }

    @Builder
    public UserResponse(Long id, String name, String email, String studentid) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.studentid = studentid;
    }
}
