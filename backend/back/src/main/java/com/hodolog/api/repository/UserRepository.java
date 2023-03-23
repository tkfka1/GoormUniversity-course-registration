package com.hodolog.api.repository;

import com.hodolog.api.domain.User;
import com.hodolog.api.request.UserSearch;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> , UserRepositoryCustom {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

    Optional<User> findByStudentid(String studentid);

}
