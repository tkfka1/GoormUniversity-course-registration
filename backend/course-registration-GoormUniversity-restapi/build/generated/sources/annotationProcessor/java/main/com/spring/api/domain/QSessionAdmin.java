package com.spring.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSessionAdmin is a Querydsl query type for SessionAdmin
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSessionAdmin extends EntityPathBase<SessionAdmin> {

    private static final long serialVersionUID = 465000123L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSessionAdmin sessionAdmin = new QSessionAdmin("sessionAdmin");

    public final StringPath accessToken = createString("accessToken");

    public final QAdmin admin;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QSessionAdmin(String variable) {
        this(SessionAdmin.class, forVariable(variable), INITS);
    }

    public QSessionAdmin(Path<? extends SessionAdmin> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSessionAdmin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSessionAdmin(PathMetadata metadata, PathInits inits) {
        this(SessionAdmin.class, metadata, inits);
    }

    public QSessionAdmin(Class<? extends SessionAdmin> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new QAdmin(forProperty("admin")) : null;
    }

}

