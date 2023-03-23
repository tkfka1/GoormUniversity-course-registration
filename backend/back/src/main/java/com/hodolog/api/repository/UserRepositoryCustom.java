package com.hodolog.api.repository;

import com.hodolog.api.domain.User;
import com.hodolog.api.request.UserSearch;
import java.util.List;

public interface UserRepositoryCustom {

    List<User> getList(UserSearch UserSearch);
}
