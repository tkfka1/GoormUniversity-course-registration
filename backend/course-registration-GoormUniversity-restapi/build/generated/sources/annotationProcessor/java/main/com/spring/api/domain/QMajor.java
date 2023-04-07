package com.spring.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMajor is a Querydsl query type for Major
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMajor extends EntityPathBase<Major> {

    private static final long serialVersionUID = -69408873L;

    public static final QMajor major = new QMajor("major");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<Lecture, QLecture> sessions1 = this.<Lecture, QLecture>createList("sessions1", Lecture.class, QLecture.class, PathInits.DIRECT2);

    public final ListPath<Professor, QProfessor> sessions2 = this.<Professor, QProfessor>createList("sessions2", Professor.class, QProfessor.class, PathInits.DIRECT2);

    public final ListPath<User, QUser> sessions3 = this.<User, QUser>createList("sessions3", User.class, QUser.class, PathInits.DIRECT2);

    public QMajor(String variable) {
        super(Major.class, forVariable(variable));
    }

    public QMajor(Path<? extends Major> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMajor(PathMetadata metadata) {
        super(Major.class, metadata);
    }

}

