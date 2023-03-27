package com.spring.api.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String studentid;

    private String email;

    private String password;

    private LocalDateTime createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Session> sessions = new ArrayList<>();

    @Builder
    public User(String studentid, String name, String email, String password) {
        this.studentid = studentid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public Session addSession() {
        Session session = Session.builder()
                .user(this)
                .build();
        sessions.add(session);

        return session;
    }

    public UserEditor.UserEditorBuilder toEditor() {
        return UserEditor.builder()
                .studentid(studentid)
                .name(name)
                .email(email)
                .password(password);
    }


    public void edit(UserEditor userEditor) {
        studentid = userEditor.getStudentid();
        name = userEditor.getName();
        email = userEditor.getEmail();
        password = userEditor.getPassword();
    }

}