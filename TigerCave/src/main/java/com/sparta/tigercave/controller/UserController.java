package com.sparta.tigercave.controller;

import com.sparta.tigercave.dto.UserDto;
import com.sparta.tigercave.entity.Users;
import com.sparta.tigercave.entity.UserRoleEnum;
import com.sparta.tigercave.repository.UserRepository;
import com.sparta.tigercave.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;
    private final UserRepository userRepository;

    @PostMapping("/signUp")
    public ResponseEntity signUp(@Validated @RequestBody UserDto.signUpDto signUpDto, BindingResult bindingresult) throws SQLIntegrityConstraintViolationException {

        //유효성 검사 실패할 경우 에러메세지 반환
        if(bindingresult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingresult.getAllErrors().toString());
        }

        //이미 존재한 user인지 확인
        Optional<Users> check_result = userRepository.findByUsername(signUpDto.getUsername());
        check_result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        UserRoleEnum role = UserRoleEnum.USER;

        usersService.signUp(signUpDto, role);
        return new ResponseEntity<>("회원가입에 성공하였습니다", HttpStatus.OK);
    }

}
