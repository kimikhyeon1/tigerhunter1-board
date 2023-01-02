package com.sparta.tigercave.service;

import com.sparta.tigercave.dto.UsersDto;
import com.sparta.tigercave.entity.Users;
import com.sparta.tigercave.entity.UsersRoleEnum;
import com.sparta.tigercave.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    @Transactional
    public void signUp(UsersDto.signUpDto signUpDto, UsersRoleEnum role){
        String password = passwordEncoder.encode(signUpDto.getPassword());
        usersRepository.save(new Users(signUpDto.getUsername(), password, role));
    }
}
