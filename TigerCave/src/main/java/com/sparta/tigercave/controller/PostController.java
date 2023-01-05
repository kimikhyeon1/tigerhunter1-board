package com.sparta.tigercave.controller;

import com.sparta.tigercave.dto.*;

import com.sparta.tigercave.jwt.JwtUtil;
import com.sparta.tigercave.security.UserDetailImpl;
import com.sparta.tigercave.service.PostLikeService;
import com.sparta.tigercave.service.PostService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    //게시글 목록
    @GetMapping("/api/posts")
    public List<PostResponseDto> getPostList(){
        return postService.getPostList();
    }

    //게시글 작성
    @PostMapping("/api/post")
//    @PreAuthorize("isAuthenticated() or hasRole(${UserRoleEnum.ADMIN.getAuthority()})")     //해당 메소드를 실행하기 전 로그인한 상태 또는 ADMIN권한을 가지고 있느냐 체크
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.createPost(postRequestDto, userDetails);
    }

    @GetMapping("/api/post/{post_id}")
    public PostResponseDto getPostById(@PathVariable Long post_id) {
        return postService.getPostById(post_id);
    }

    // 게시글 수정
    @PutMapping("/api/post/{post_id}")
    public PostResponseDto updatePost(@PathVariable Long post_id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.updatePost(post_id, postRequestDto, userDetails);
    }
    // 게시글 삭제
    @DeleteMapping("/api/post/{post_id}")
    public ResponseEntity deletePost(@PathVariable Long post_id, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.deletePost(post_id, userDetails);
    }

}