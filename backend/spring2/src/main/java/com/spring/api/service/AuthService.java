package com.spring.api.service;

import com.spring.api.crypto.PasswordEncoder;
import com.spring.api.domain.User;
import com.spring.api.domain.UserEditor;
import com.spring.api.exception.AlreadyExistsEmailException;
import com.spring.api.exception.InvalidSigninInformation;
import com.spring.api.exception.UserNotFound;
import com.spring.api.repository.UserRepository;
import com.spring.api.request.UserEdit;
import com.spring.api.request.UserSearch;
import com.spring.api.response.UserResponse;
import com.spring.api.request.Login;
import com.spring.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signin(Login login) {
        User user = userRepository.findByStudentid(login.getStudentid())
                .orElseThrow(InvalidSigninInformation::new);

        PasswordEncoder encoder = new PasswordEncoder();
        var matches = encoder.matches(login.getPassword(), user.getPassword());
        if (!matches) {
            throw new InvalidSigninInformation();
        }

        return user.getId();
    }

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByStudentid(signup.getStudentid());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        PasswordEncoder encoder = new PasswordEncoder();
        String encryptedPassword = encoder.encrpyt(signup.getPassword());

        var user = User.builder()
                .studentid(signup.getStudentid())
                .email(signup.getEmail())
                .password(encryptedPassword)
                .name(signup.getName())
                .build();
        userRepository.save(user);
    }


    public UserResponse get(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .studentid(user.getStudentid())
                .build();
    }

    public List<UserResponse> getList(UserSearch userSearch) {
        return userRepository.getList(userSearch).stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }
    
    // 비밀번호 있을 시
    @Transactional
    public void edit(Long id, UserEdit userEdit) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        UserEditor.UserEditorBuilder editorBuilder = user.toEditor();

        PasswordEncoder encoder = new PasswordEncoder();
        String encryptedPassword = encoder.encrpyt(userEdit.getPassword());

        UserEditor userEditor = editorBuilder
                .name(userEdit.getName())
                .studentid(userEdit.getStudentid())
                .email(userEdit.getEmail())
                .password(encryptedPassword)
                .build();

        user.edit(userEditor);
    }
    
    // 비밀번호 없을 시
    @Transactional
    public void edit2(Long id, UserEdit userEdit) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        UserEditor.UserEditorBuilder editorBuilder = user.toEditor();

        UserEditor userEditor = editorBuilder
                .name(userEdit.getName())
                .studentid(userEdit.getStudentid())
                .email(userEdit.getEmail())
                .build();

        user.edit(userEditor);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        userRepository.delete(user);
    }




}
