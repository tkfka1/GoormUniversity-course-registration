package com.spring.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProfessor is a Querydsl query type for Professor
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProfessor extends EntityPathBase<Professor> {

    private static final long serialVersionUID = 147122509L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfessor professor = new QProfessor("professor");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<LectureClass, QLectureClass> lectureClassSessions = this.<LectureClass, QLectureClass>createList("lectureClassSessions", LectureClass.class, QLectureClass.class, PathInits.DIRECT2);

    public final QMajor major;

    public final StringPath name = createString("name");

    public QProfessor(String variable) {
        this(Professor.class, forVariable(variable), INITS);
    }

    public QProfessor(Path<? extends Professor> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProfessor(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProfessor(PathMetadata metadata, PathInits inits) {
        this(Professor.class, metadata, inits);
    }

    public QProfessor(Class<? extends Professor> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.major = inits.isInitialized("major") ? new QMajor(forProperty("major")) : null;
    }

}

