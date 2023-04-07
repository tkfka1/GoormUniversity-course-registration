package com.spring.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTakeLecture is a Querydsl query type for TakeLecture
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTakeLecture extends EntityPathBase<TakeLecture> {

    private static final long serialVersionUID = 162557525L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTakeLecture takeLecture = new QTakeLecture("takeLecture");

    public final NumberPath<Long> credit = createNumber("credit", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLectureClass lectureClass;

    public final DateTimePath<java.time.LocalDateTime> takeDate = createDateTime("takeDate", java.time.LocalDateTime.class);

    public final QUser user;

    public QTakeLecture(String variable) {
        this(TakeLecture.class, forVariable(variable), INITS);
    }

    public QTakeLecture(Path<? extends TakeLecture> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTakeLecture(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTakeLecture(PathMetadata metadata, PathInits inits) {
        this(TakeLecture.class, metadata, inits);
    }

    public QTakeLecture(Class<? extends TakeLecture> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lectureClass = inits.isInitialized("lectureClass") ? new QLectureClass(forProperty("lectureClass"), inits.get("lectureClass")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

