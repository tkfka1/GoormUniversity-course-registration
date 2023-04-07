package com.spring.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSessionUser is a Querydsl query type for SessionUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSessionUser extends EntityPathBase<SessionUser> {

    private static final long serialVersionUID = -1508410657L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSessionUser sessionUser = new QSessionUser("sessionUser");

    public final StringPath accessToken = createString("accessToken");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QSessionUser(String variable) {
        this(SessionUser.class, forVariable(variable), INITS);
    }

    public QSessionUser(Path<? extends SessionUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSessionUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSessionUser(PathMetadata metadata, PathInits inits) {
        this(SessionUser.class, metadata, inits);
    }

    public QSessionUser(Class<? extends SessionUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

