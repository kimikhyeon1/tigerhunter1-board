package com.sparta.tigercave.controller;

import com.sparta.tigercave.security.UserDetailImpl;
import com.sparta.tigercave.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/post/comment")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 좋아요 추가
    @PostMapping("/{comment_id}/like")
    public ResponseEntity addLike(@RequestParam Long comment_id, @AuthenticationPrincipal UserDetailImpl userDetail) {
        Boolean likeOpt = commentLikeService.addorDeleteLike(comment_id, BigInteger.valueOf(userDetail.getUserId()));

        // 좋아요 등록
        if (likeOpt) {
            return new ResponseEntity<>("좋아요!", HttpStatus.OK);
        }

        // 좋아요 삭제
        return new ResponseEntity<>("좋아요 취소", HttpStatus.OK);
    }

}
