package com.sparta.tigercave.service;

import com.sparta.tigercave.dto.CommentRequestDto;
import com.sparta.tigercave.dto.CommentResponseDto;
import com.sparta.tigercave.entity.Comment;
import com.sparta.tigercave.entity.Users;
import com.sparta.tigercave.exception.CustomException;
import com.sparta.tigercave.jwt.JwtUtil;
import com.sparta.tigercave.repository.CommentRepository;
import com.sparta.tigercave.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import static com.sparta.tigercave.entity.UsersRoleEnum.ADMIN;
import static com.sparta.tigercave.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;

    // 댓글 작성하기
    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND) // 게시글이 존재하지 않을 때
        );

        // Request에서 토근 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰에서 사용자 정보 가져오기
        claims = jwtUtil.getUserInfoFromToken(token);

        // 토큰에서 가져온 사용자 정보를 사용하여 Users객체 가져오기
        Users user = usersRepository.findByUsername(claims.getSubject()).get();

        Comment comment = commentRepository.saveAndFlush(new Comment(user, post, commentRequestDto));
        return new CommentResponseDto(comment);
    }

    @Transactional
    // 댓글 수정하기
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND) // 댓글이 존재 하지 않을 때
        );

        // Request에서 토근 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰에서 사용자 정보 가져오기
        claims = jwtUtil.getUserInfoFromToken(token);

        // 토큰에서 가져온 사용자 정보를 사용하여 Users의 인스턴스 가져오기
        Users user = usersRepository.findByUsername(claims.getSubject()).get();

        //로그인한 유저와 댓글을 작성한 유저의 일치 여부를 확인하기
        if(comment.getUsername() != user.getUsername() && user.getRole() != ADMIN) { // 작성자도 어드민도 아니면 댓글을 수정 할 수 없다.
            throw new CustomException(INVALID_USER); // 작성자와 유저가 일치하지 않을 때
        }
        comment.update(commentRequestDto);
        return new CommentResponseDto(comment);
    }

    @Transactional
    // 댓글 삭제하기
    public ResponseEntity deleteComment(Long id, HttpServletRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND) // 댓글이 존재하지 않을 때
        );
        // Request에서 토근 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰에서 사용자 정보 가져오기
        claims = jwtUtil.getUserInfoFromToken(token);

        // 토큰에서 가져온 사용자 정보를 사용하여 Users의 인스턴스 가져오기
        Users user = usersRepository.findByUsername(claims.getSubject()).get();

        //로그인한 유저와 댓글을 작성한 유저의 일치 여부를 확인하기
        if(comment.getUsername() != user.getUsername() && user.getRole() != ADMIN) { // 작성자도 어드민도 아니면 댓글을 수정 할 수 없다.
            throw new CustomException(INVALID_USER); // 작성자와 유저가 일치하지 않을 때
        }
        commentRepository.deleteById(id);
        return new ResponseEntity("댓글이 삭제되었습니다.", HttpStatus.OK);
    }
}
