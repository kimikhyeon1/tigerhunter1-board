package com.sparta.tigercave.controller;

import com.sparta.tigercave.dto.*;
import com.sparta.tigercave.entity.StatusEnum;
import com.sparta.tigercave.entity.UserRoleEnum;
import com.sparta.tigercave.exception.CustomException;
import com.sparta.tigercave.jwt.JwtUtil;
import com.sparta.tigercave.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

import static com.sparta.tigercave.exception.ErrorCode.INVALID_AUTH_TOKEN;
import static com.sparta.tigercave.exception.ErrorCode.INVALID_TOKEN;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;


    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView();
    }

//    @PostMapping("/posts")
//    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
//        String token = jwtUtil.resolveToken(request);
//
//        if (token == null) {
//            throw new CustomException(INVALID_TOKEN);
//        }
//        AuthenticatedUserInfoDto authenticatedUserInfoDto = jwtUtil.validateAndGetUserInfo(token);
//        return postService.createPost(requestDto, authenticatedUserInfoDto.getUsername());
//    }

    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        return postService.createPost(postRequestDto, userDetails);
    }


    @GetMapping("/posts/id")
    public PostResponseDto getPostById(@RequestParam Long id) {
        return postService.getPostById(id);
    }

//    @GetMapping("/posts")
//    public List<PostResponseDto> getAllPostsOrGetpostByUsername(@RequestBody(required = false) UserNameRequestDto requestDto) {
//        if (requestDto == null) {
//            return postService.getAllPosts();
//        } else {
//            return postService.getPostByUsername(requestDto);
//        }
//    }

    private boolean isAdmin(UserRoleEnum usersRoleEnum) {
        return usersRoleEnum == UserRoleEnum.ADMIN;
    }

    @PutMapping("/admin/posts/{id}")
    public PostResponseDto updatePostAdmin(@PathVariable Long id, @RequestBody UpdateRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            throw new CustomException(INVALID_TOKEN);
        }
        AuthenticatedUserInfoDto authenticatedUserInfoDto = jwtUtil.validateAndGetUserInfo(token);
        if (!this.isAdmin(authenticatedUserInfoDto.getUsersRoleEnum())) {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
        return postService.updateAdmin(id, requestDto);
    }

    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody UpdateRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            throw new CustomException(INVALID_TOKEN);
        }
        AuthenticatedUserInfoDto authenticatedUserInfoDto = jwtUtil.validateAndGetUserInfo(token);
        return postService.update(id, requestDto, authenticatedUserInfoDto.getUsername());
    }

    @DeleteMapping("/admin/posts/{id}")
    public ResponseEntity<StatusResponseDto> deletePostAdmin(@PathVariable Long id, HttpServletRequest request) {
        StatusResponseDto responseDto = new StatusResponseDto(StatusEnum.OK, "삭제 성공");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            throw new CustomException(INVALID_TOKEN);
        }
        AuthenticatedUserInfoDto authenticatedUserInfoDto = jwtUtil.validateAndGetUserInfo(token);
        if (!this.isAdmin(authenticatedUserInfoDto.getUsersRoleEnum())) {
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
        postService.deletePostAdmin(id);
        return new ResponseEntity<>(responseDto, headers, HttpStatus.OK);
    }
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<StatusResponseDto> deletePost(@PathVariable Long id, HttpServletRequest request) {
        StatusResponseDto responseDto = new StatusResponseDto(StatusEnum.OK, "삭제 성공");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));


        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            throw new CustomException(INVALID_TOKEN);
        }
        AuthenticatedUserInfoDto authenticatedUserInfoDto = jwtUtil.validateAndGetUserInfo(token);
        postService.deletePost(id, authenticatedUserInfoDto.getUsername());
        return new ResponseEntity<>(responseDto, headers, HttpStatus.OK);
    }
}