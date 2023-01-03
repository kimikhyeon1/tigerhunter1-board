package com.sparta.tigercave.service;

import com.sparta.tigercave.dto.UserDto;
import com.sparta.tigercave.entity.Users;
import com.sparta.tigercave.entity.UsersRoleEnum;
import com.sparta.tigercave.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Transactional
    public void signUp(UserDto.signUpDto signUpDto, UsersRoleEnum role) throws SQLIntegrityConstraintViolationException {
        String password = passwordEncoder.encode(signUpDto.getPassword());
        userRepository.save(new Users(signUpDto.getUsername(), password, role));
    }
}
