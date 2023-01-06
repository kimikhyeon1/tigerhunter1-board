package com.sparta.tigercave.controller;

import com.sparta.tigercave.dto.*;

import com.sparta.tigercave.jwt.JwtUtil;
import com.sparta.tigercave.security.UserDetailImpl;
import com.sparta.tigercave.service.PostLikeService;
import com.sparta.tigercave.service.PostService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    public ResponseEntity getPostList(){
        if(postService.getPostList()==null){
            return new ResponseEntity("작성된 게시글이 없습니다.", HttpStatus.OK);
        }
        return new ResponseEntity(postService.getPostList(), HttpStatus.OK);
    }

    //게시글 작성
    @PostMapping("/api/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.createPost(postRequestDto, userDetails);
    }

    @GetMapping("/api/post/{postId}")
    public PostResponseDto getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    // 게시글 수정
    @PutMapping("/api/post/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.updatePost(postId, postRequestDto, userDetails);
    }
    // 게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.deletePost(postId, userDetails);
    }

}