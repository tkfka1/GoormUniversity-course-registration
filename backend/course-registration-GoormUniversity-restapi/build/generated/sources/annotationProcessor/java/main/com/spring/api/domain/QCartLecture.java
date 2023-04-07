package com.spring.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCartLecture is a Querydsl query type for CartLecture
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCartLecture extends EntityPathBase<CartLecture> {

    private static final long serialVersionUID = 1251031452L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCartLecture cartLecture = new QCartLecture("cartLecture");

    public final NumberPath<Long> credit = createNumber("credit", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLectureClass lectureClass;

    public final DateTimePath<java.time.LocalDateTime> takeDate = createDateTime("takeDate", java.time.LocalDateTime.class);

    public final QUser user;

    public QCartLecture(String variable) {
        this(CartLecture.class, forVariable(variable), INITS);
    }

    public QCartLecture(Path<? extends CartLecture> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCartLecture(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCartLecture(PathMetadata metadata, PathInits inits) {
        this(CartLecture.class, metadata, inits);
    }

    public QCartLecture(Class<? extends CartLecture> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lectureClass = inits.isInitialized("lectureClass") ? new QLectureClass(forProperty("lectureClass"), inits.get("lectureClass")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

