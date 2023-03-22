package com.finalproject.service;


import com.finalproject.crypto.PasswordEncoder;
import com.finalproject.domain.User;
import com.finalproject.excption.AlreadyExistsEmailException;
import com.finalproject.excption.InvalidSigninInformation;
import com.finalproject.repository.UserRepository;
import com.finalproject.request.Login;
import com.finalproject.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    // 인증하는 서비스 SCrypt 암호화 사용

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 로그인
    @Transactional
    public Long signin(Login login) {
        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSigninInformation::new);

        var matches = passwordEncoder.matches(login.getPassword(), user.getPassword());
        if (!matches) {
            throw new InvalidSigninInformation();
        }

        return user.getId();

    }

    // 회원 가입
    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        String encryptedPassword = passwordEncoder.encrypt(signup.getPassword());

        var user = User.builder()
                .email(signup.getEmail())
                .password(encryptedPassword)
                .name(signup.getName())
                .build();
        userRepository.save(user);
        
    }
}
