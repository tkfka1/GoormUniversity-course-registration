package com.spring.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QOpenTime is a Querydsl query type for OpenTime
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOpenTime extends EntityPathBase<OpenTime> {

    private static final long serialVersionUID = 1887949209L;

    public static final QOpenTime openTime = new QOpenTime("openTime");

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public QOpenTime(String variable) {
        super(OpenTime.class, forVariable(variable));
    }

    public QOpenTime(Path<? extends OpenTime> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOpenTime(PathMetadata metadata) {
        super(OpenTime.class, metadata);
    }

}

