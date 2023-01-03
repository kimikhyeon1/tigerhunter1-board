package com.sparta.tigercave.controller;

import com.sparta.tigercave.dto.UsersDto;
import com.sparta.tigercave.entity.Users;
import com.sparta.tigercave.entity.UsersRoleEnum;
import com.sparta.tigercave.repository.UsersRepository;
import com.sparta.tigercave.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final UsersRepository usersRepository;

    private final JwtUtil jwtUtil;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody @Validated UsersDto.signUpRequestDto signUpDto, BindingResult bindingresult){

        //유효성 검사 실패할 경우 에러메세지 반환
        if(bindingresult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingresult.getAllErrors().toString());
        }

        //이미 존재한 user인지 확인
        Optional<Users> check_result = usersRepository.findByUsername(signUpDto.getUsername());
        check_result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        UsersRoleEnum role = UsersRoleEnum.USER;
        UsersDto.signUpRequestDto signUpRequestDto = new UsersDto.signUpRequestDto();

        if(signUpRequestDto.isAdmin()){
            if(!signUpRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
                throw new IllegalArgumentException("관리자 암호가 일치하지 않아 등록이 불가합니다.");
            }
            role = UsersRoleEnum.ADMIN;
        }

        usersService.signUp(signUpDto, role);
        return new ResponseEntity<>("회원가입에 성공하였습니다", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password, HttpServletResponse response){

        UsersDto.loginResponseDto users = usersService.login(username, password);

        if(!users.equals(null)){
            response.addHeader(users.getUsername(), jwtUtil.createToken(users.getUsername(), users.getRole()));
            return new ResponseEntity("로그인에 성공하였습니다.", HttpStatus.OK);
        }else{
            return new ResponseEntity("회원정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
