package com.spring.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLectureClass is a Querydsl query type for LectureClass
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLectureClass extends EntityPathBase<LectureClass> {

    private static final long serialVersionUID = -1550974212L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLectureClass lectureClass = new QLectureClass("lectureClass");

    public final ListPath<CartLecture, QCartLecture> cartLectures = this.<CartLecture, QCartLecture>createList("cartLectures", CartLecture.class, QCartLecture.class, PathInits.DIRECT2);

    public final NumberPath<Long> classMax = createNumber("classMax", Long.class);

    public final NumberPath<Long> classMin = createNumber("classMin", Long.class);

    public final NumberPath<Long> classPeople = createNumber("classPeople", Long.class);

    public final StringPath explanation = createString("explanation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLecture lecture;

    public final NumberPath<Long> period = createNumber("period", Long.class);

    public final QProfessor professor;

    public final ListPath<TakeLecture, QTakeLecture> takeLectures = this.<TakeLecture, QTakeLecture>createList("takeLectures", TakeLecture.class, QTakeLecture.class, PathInits.DIRECT2);

    public final NumberPath<Long> week = createNumber("week", Long.class);

    public QLectureClass(String variable) {
        this(LectureClass.class, forVariable(variable), INITS);
    }

    public QLectureClass(Path<? extends LectureClass> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLectureClass(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLectureClass(PathMetadata metadata, PathInits inits) {
        this(LectureClass.class, metadata, inits);
    }

    public QLectureClass(Class<? extends LectureClass> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lecture = inits.isInitialized("lecture") ? new QLecture(forProperty("lecture"), inits.get("lecture")) : null;
        this.professor = inits.isInitialized("professor") ? new QProfessor(forProperty("professor"), inits.get("professor")) : null;
    }

}

