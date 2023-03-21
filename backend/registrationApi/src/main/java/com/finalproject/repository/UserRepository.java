package com.finalproject.repository;

import com.finalproject.domain.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Student, Long> {

//        Optional<Student> findByStudent_idAndStudent_pw(String student_id, String student_pw);
//
        Optional<Student> findByStudentidAndPw(String studentid, String pw);


}
