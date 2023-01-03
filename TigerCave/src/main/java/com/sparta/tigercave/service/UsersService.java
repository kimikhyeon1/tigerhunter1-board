package com.sparta.tigercave.service;

import com.sparta.tigercave.dto.UsersDto;
import com.sparta.tigercave.entity.User;
<<<<<<< Updated upstream
import com.sparta.tigercave.entity.UserRoleEnum;
=======
import com.sparta.tigercave.entity.UsersRoleEnum;
>>>>>>> Stashed changes
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
    public void signUp(UsersDto.signUpRequestDto signUpDto, UserRoleEnum role) {
        String password = passwordEncoder.encode(signUpDto.getPassword());
        usersRepository.save(new User(signUpDto.getUsername(), password, role));
    }

    @Transactional(readOnly = true)
    public UsersDto.loginResponseDto login(String username, String password) {

        User user = usersRepository.findByUsername(username).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(PASSWORD_NOT_FOUND);
        }
        return new UsersDto.loginResponseDto(user.getUsername(), user.getPassword(), user.getRole());
    }
}
