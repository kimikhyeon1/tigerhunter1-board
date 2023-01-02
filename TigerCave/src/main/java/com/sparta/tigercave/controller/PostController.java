package com.sparta.tigercave.controller;

import com.sparta.tigercave.dto.UpdateRequestDto;
import com.sparta.tigercave.jwt.JwtUtil;
import com.sparta.tigercave.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView();
    }

    @PostMapping("/posts")
    public postResponseDto createpost(@RequestBody postRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
        AuthenticatedUserInfoDto authenticatedUserInfoDto = jwtUtil.validateAndGetUserInfo(token);
        return postService.createpost(requestDto, authenticatedUserInfoDto.getUsername());
    }

//    @GetMapping("/posts")
//    public List<postResponseDto> getAllposts() {
//        return postService.getAllposts();
//    }

    @GetMapping("/posts/id")
    public postResponseDto getpostById(@RequestParam Long id) {
        return postService.getpostById(id);
    }

    @GetMapping("/posts")
    public List<postResponseDto> getAllpostsOrGetpostByUsername(@RequestBody(required = false) UsernameRequestDto requestDto) {
        if (requestDto == null) {
            return postService.getAllposts();
        } else {
            return postService.getpostByUsername(requestDto);
        }
    }

    private boolean isAdmin(UserRoleEnum userRoleEnum) {
        return userRoleEnum == UserRoleEnum.ADMIN;
    }

    @PutMapping("/admin/posts/{id}")
    public postResponseDto updatepostAdmin(@PathVariable Long id, @RequestBody UpdateRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
        AuthenticatedUserInfoDto authenticatedUserInfoDto = jwtUtil.validateAndGetUserInfo(token);
        if (!this.isAdmin(authenticatedUserInfoDto.getUserRoleEnum())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        return postService.updateAdmin(id, requestDto);
    }

    @PutMapping("/posts/{id}")
    public postResponseDto updatepost(@PathVariable Long id, @RequestBody UpdateRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
        AuthenticatedUserInfoDto authenticatedUserInfoDto = jwtUtil.validateAndGetUserInfo(token);
        return postService.update(id, requestDto, authenticatedUserInfoDto.getUsername());
    }

    @DeleteMapping("/admin/posts/{id}")
    public ResponseEntity<StatusResponseDto> deletepostAdmin(@PathVariable Long id, HttpServletRequest request) {
        StatusResponseDto responseDto = new StatusResponseDto(StatusEnum.OK, "삭제 성공");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        responseDto.setStatus(StatusEnum.OK);
//        responseDto.setMessage("삭제 성공");

        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
        AuthenticatedUserInfoDto authenticatedUserInfoDto = jwtUtil.validateAndGetUserInfo(token);
        if (!this.isAdmin(authenticatedUserInfoDto.getUserRoleEnum())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        postService.deletepostAdmin(id);
        return new ResponseEntity<>(responseDto, headers, HttpStatus.OK);
    }
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<StatusResponseDto> deletepost(@PathVariable Long id, HttpServletRequest request) {
        StatusResponseDto responseDto = new StatusResponseDto(StatusEnum.OK, "삭제 성공");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        responseDto.setStatus(StatusEnum.OK);
//        responseDto.setMessage("삭제 성공");

        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
        AuthenticatedUserInfoDto authenticatedUserInfoDto = jwtUtil.validateAndGetUserInfo(token);
        postService.deletepost(id, authenticatedUserInfoDto.getUsername());
        return new ResponseEntity<>(responseDto, headers, HttpStatus.OK);
    }
}