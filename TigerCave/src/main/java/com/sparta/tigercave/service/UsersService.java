package com.sparta.tigercave.service;

import com.sparta.tigercave.dto.UsersDto;
import com.sparta.tigercave.entity.Users;
import com.sparta.tigercave.entity.UsersRoleEnum;
import com.sparta.tigercave.exception.CustomException;
import com.sparta.tigercave.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.tigercave.exception.ErrorCode.PASSWORD_NOT_FOUND;
import static com.sparta.tigercave.exception.ErrorCode.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    @Transactional
    public void signUp(UsersDto.signUpRequestDto signUpDto, UsersRoleEnum role) {
        String password = passwordEncoder.encode(signUpDto.getPassword());
        usersRepository.save(new Users(signUpDto.getUsername(), password, role));
    }

    @Transactional(readOnly = true)
    public UsersDto.loginResponseDto login(String username, String password) {

        Users users = usersRepository.findByUsername(username).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, users.getPassword())) {
            throw new CustomException(PASSWORD_NOT_FOUND);
        }
        return new UsersDto.loginResponseDto(users.getUsername(), users.getPassword(), users.getRole());
    }
}
