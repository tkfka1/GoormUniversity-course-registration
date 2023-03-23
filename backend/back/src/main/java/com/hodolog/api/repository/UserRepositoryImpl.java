package com.hodolog.api.repository;


import com.hodolog.api.domain.User;
import com.hodolog.api.request.UserSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hodolog.api.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> getList(UserSearch userSearch) {
        return jpaQueryFactory.selectFrom(user)
//                .limit(userSearch.getSize())
//                .offset(userSearch.getOffset())
                .orderBy(user.id.desc())
                .fetch();
    }
}
