package com.sparta.tigercave.controller;


import com.sparta.tigercave.dto.CommentRequestDto;
import com.sparta.tigercave.dto.CommentResponseDto;
import com.sparta.tigercave.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성하기
    @PostMapping("/{postId}/comment")
    public CommentResponseDto createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.createComment(postId, commentRequestDto, userDetails.getUsername());
    }

    // 댓글 수정하기
    @PutMapping("/comment/{commentId}")
    public CommentResponseDto updateCommment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.updateComment(commentId, commentRequestDto, userDetails.getUsername());
    }

    // 댓글 삭제하기
    @DeleteMapping("comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.deleteComment(commentId, userDetails.getUsername());
    }
}
