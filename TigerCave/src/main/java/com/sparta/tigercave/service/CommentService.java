package com.sparta.tigercave.service;

import com.sparta.tigercave.dto.CommentRequestDto;
import com.sparta.tigercave.dto.CommentResponseDto;
import com.sparta.tigercave.entity.Comment;
import com.sparta.tigercave.entity.Post;
import com.sparta.tigercave.repository.CommentRepository;
import com.sparta.tigercave.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 작성하기
    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.saveAndFlush(new Comment(post, commentRequestDto));
        return new CommentResponseDto(comment);
    }

    // 댓글 수정하기
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        comment.update(commentRequestDto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제하기
    public ResponseEntity deleteComment(Long id) {
        commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        commentRepository.deleteById(id);
        return new ResponseEntity("댓글이 삭제되었습니다.", HttpStatus.OK);
    }
}
