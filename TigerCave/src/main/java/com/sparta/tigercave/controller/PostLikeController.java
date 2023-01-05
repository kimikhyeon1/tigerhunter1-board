package com.sparta.tigercave.controller;

import com.sparta.tigercave.jwt.JwtUtil;
import com.sparta.tigercave.security.UserDetailImpl;
import com.sparta.tigercave.service.PostLikeService;
import com.sparta.tigercave.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")

public class PostLikeController {

    private final PostLikeService postLikeService;



    @PostMapping("/{postId}/like")
    public ResponseEntity like(@PathVariable Long postId, @AuthenticationPrincipal UserDetailImpl userDetails){
        boolean likeCheck = postLikeService.saveLikes(postId,userDetails);
        if (likeCheck){
            return new ResponseEntity<>("좋아요!", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("이미 좋아요를 누르셨습니다!",HttpStatus.OK);
        }
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity unLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailImpl userDetails){
        boolean unLikeCheck = postLikeService.deleteLikes(postId,userDetails);
        if (unLikeCheck){
            return new ResponseEntity<>("좋아요 취소!", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("이미 좋아요 취소를 누르셨습니다!",HttpStatus.OK);
        }
    }

}
