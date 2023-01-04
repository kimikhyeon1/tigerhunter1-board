package com.sparta.tigercave.controller;

import com.sparta.tigercave.dto.*;

import com.sparta.tigercave.jwt.JwtUtil;
import com.sparta.tigercave.security.UserDetailImpl;
import com.sparta.tigercave.service.PostLikeService;
import com.sparta.tigercave.service.PostService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class PostController {
    private final PostService postService;
    private final PostLikeService postLikeService;
    private final JwtUtil jwtUtil;



    @GetMapping("/api/post")
    public List<PostResponseDto> getPostList(){
        return postService.getPostList();
    }
    @PostMapping("/api/posts/{postId}/like")
    public Long addLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailImpl userDetails){
        return postLikeService.addLike(postId,userDetails);
    }

    @PostMapping("/api/post")
    public PostResponseDto createPost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.createPost(id, postRequestDto, userDetails);
    }

    @PutMapping("/api/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.updatePost(id, postRequestDto, userDetails);
    }

    @DeleteMapping("/api/post/{id}")
    public ResponseEntity deletePost(@PathVariable Long post_id, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.deletePost(post_id, userDetails);
    }

}