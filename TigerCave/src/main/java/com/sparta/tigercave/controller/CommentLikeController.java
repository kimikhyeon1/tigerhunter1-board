package com.sparta.tigercave.controller;

import com.sparta.tigercave.exception.CustomException;
import com.sparta.tigercave.security.UserDetailImpl;
import com.sparta.tigercave.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.sparta.tigercave.exception.ErrorCode.*;

@RestController
@RequestMapping("/api/post/comment")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 좋아요 등록
    @PostMapping("/{comment_id}/like")
    public ResponseEntity addLike(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailImpl userDetail) {
        Boolean likeOpt = commentLikeService.addLike(comment_id, userDetail.getUserId());

        // 좋아요 등록, 이미 좋아요를 한 사용자의 경우 Exception
        if (likeOpt) {
            return new ResponseEntity<>("좋아요!", HttpStatus.OK);
        }else {
            throw new CustomException(ALREADY_LIKE_USER);
        }
    }

    //좋아요 취소
    @DeleteMapping("/{comment_id}/like")
    public ResponseEntity deleteLike(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailImpl userDetail) {
        Boolean likeOpt = commentLikeService.deleteLike(comment_id, userDetail.getUserId());

        // 좋아요 취소, 아직 좋아요를 안 한 사용자의 경우 Exception
        if (likeOpt) {
            return new ResponseEntity<>("좋아요 취소", HttpStatus.OK);
        } else {
            throw new CustomException(NOT_YET_LIKE_USER);
        }
    }

}
