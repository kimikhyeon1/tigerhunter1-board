package com.sparta.tigercave.service;

import com.sparta.tigercave.dto.UsersDto;
import com.sparta.tigercave.entity.Users;
import com.sparta.tigercave.entity.UsersRoleEnum;
import com.sparta.tigercave.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    @Transactional
    public void signUp(UsersDto.signUpRequestDto signUpDto, UsersRoleEnum role){
        String password = passwordEncoder.encode(signUpDto.getPassword());
        usersRepository.save(new Users(signUpDto.getUsername(), password, role));
    }

    @Transactional(readOnly = true)
    public UsersDto.loginResponseDto login(String username, String password){

        Users users = usersRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 존재하지 않습니다."));

        if(passwordEncoder.matches(users.getPassword(), password) && users.checkUserName(username)){
            return new UsersDto.loginResponseDto(users.getUsername(), users.getPassword(), users.getRole());
        }
        return null;
    }
}
