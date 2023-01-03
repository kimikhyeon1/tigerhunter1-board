package com.sparta.tigercave.controller;


import com.sparta.tigercave.dto.CommentRequestDto;
import com.sparta.tigercave.dto.CommentResponseDto;
import com.sparta.tigercave.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성하기
    @PostMapping("/{post_id}/comment")
    public CommentResponseDto createComment(@PathVariable Long post_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.createComment(post_id, commentRequestDto, userDetails);
    }

    // 댓글 수정하기
    @PutMapping("/comment/{comment_id}")
    public CommentResponseDto updateCommment(@PathVariable Long comment_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.updateComment(comment_id, commentRequestDto, userDetails);
    }

    // 댓글 삭제하기
    @DeleteMapping("comment/{comment_id}")
    public ResponseEntity deleteComment(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.deleteComment(comment_id, userDetails);
    }

}
