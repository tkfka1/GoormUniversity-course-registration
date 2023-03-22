package com.finalproject.service;

import com.finalproject.crypto.PasswordEncoder;
import com.finalproject.domain.User;
import com.finalproject.excption.AlreadyExistsEmailException;
import com.finalproject.excption.InvalidSigninInformation;
import com.finalproject.repository.UserRepository;
import com.finalproject.request.Login;
import com.finalproject.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.rmi.AlreadyBoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() { userRepository.deleteAll(); }

    @Autowired
    private PasswordEncoder encrypt;


    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        //given
        Signup signup = Signup.builder()
                .email("test@test.com")
                .password("1234")
                .name("test")
                .build();


        //when
        //회원 가입 서비스
        authService.signup(signup);

        //then
        Assertions.assertEquals(1, userRepository.count());

//        User user = userRepository.findAll().iterator().next();
//        assertEquals("test@test.com" , user.getEmail());
//        assertNotNull(user.getPassword());
//        assertEquals("1234" , user.getPassword());
//        assertEquals("test" , user.getName());


    }

    @Test
    @DisplayName("직접 데이터 데이터베이스 넣고 로그인")
    void test2() {
        //given

        String encryptedPassword = encrypt.encrypt("1234");
        User user = User.builder()
                .email("test@test.com")
                .password(encryptedPassword)
                .name("test")
                .build();
        userRepository.save(user);

        Login login = Login.builder()
                .email("test@test.com")
                .password("1234")
                .build();

        authService.signin(login);

        Long userId = authService.signin(login);

        assertNotNull(userId);


    }

    @Test
    @DisplayName("직접 데이터 데이터베이스 넣고 로그인 비밀번호 틀림")
    void test3() {
        //given

        String encryptedPassword = encrypt.encrypt("1234");
        User user = User.builder()
                .email("test@test.com")
                .password(encryptedPassword)
                .name("test")
                .build();
        userRepository.save(user);

        Login login = Login.builder()
                .email("test@test.com")
                .password("12345")
                .build();

        Assertions.assertThrows(InvalidSigninInformation.class,
                () -> authService.signin(login));

        authService.signin(login);

        Long userId = authService.signin(login);

        assertNotNull(userId);


    }

    @Test
    @DisplayName("중복 이메일")
    void test4() {
        //given
        String encryptedPassword = encrypt.encrypt("1234");
        User user = User.builder()
                .email("test@test.com")
                .password(encryptedPassword)
                .name("test")
                .build();
        userRepository.save(user);

        Signup signup = Signup.builder()
                .email("test@test.com")
                .password("1234")
                .build();

        // expected
        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));


    }

}