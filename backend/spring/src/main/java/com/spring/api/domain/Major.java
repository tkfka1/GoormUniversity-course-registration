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
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    private Long id;
    @Column(name = "major_name")
    private String name;


    @Builder
    public Major(String name) {
        this.name = name;
    }



    public MajorEditor.MajorEditorBuilder toEditor() {
        return MajorEditor.builder()
                .name(name);
    }

    public void edit(MajorEditor majorEditor) {
        name = majorEditor.getName();
    }


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "major")
    private List<test> sessions1 = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "major")
    private List<Professor> sessions2 = new ArrayList<>();

    public test addSession() {
        test session = test.builder()
                .major(this)
                .build();
        sessions1.add(session);

        return session;
    }

    public Professor addProfessor() {
        Professor professor = Professor.builder()
                .major(this)
                .build();
        sessions2.add(professor);

        return professor;
    }

}
