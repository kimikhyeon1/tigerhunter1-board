package com.sparta.tigercave.service;

import com.sparta.tigercave.dto.CommentRequestDto;
import com.sparta.tigercave.dto.CommentResponseDto;
import com.sparta.tigercave.entity.Comment;
import com.sparta.tigercave.entity.Post;
import com.sparta.tigercave.entity.User;
import com.sparta.tigercave.exception.CustomException;
import com.sparta.tigercave.repository.CommentRepository;
import com.sparta.tigercave.repository.PostRepository;
import com.sparta.tigercave.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static com.sparta.tigercave.entity.UserRoleEnum.ADMIN;
import static com.sparta.tigercave.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    // 댓글 작성하기
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, String username) {


        // 댓글을 작성하기 위한 게시글이 존재하는지 확인한다.
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND) // 게시글이 존재하지 않을 때
        );
        // 사용자 정보를 사용하여 Users의 인스턴스 가져오기
        User user = userRepository.findByUsername(username).get();
        // DB에 댓글을 저장하기
        Comment comment = commentRepository.saveAndFlush(new Comment(user, post, commentRequestDto));
        // 영속성 컨텍스트 초기화, DB에서 저장된 값을 조회하기 위함
        entityManager.clear();
        // DB에서 저장된 댓글을 조회하여 반환하기
        return new CommentResponseDto(commentRepository.findById(comment.getId()).get());
    }

    @Transactional
    // 댓글 수정하기
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, String username) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND) // 댓글이 존재 하지 않을 때
        );

        // 사용자 정보를 조회하여 Users의 인스턴스 가져오기
        User user = userRepository.findByUsername(username).get();

        //로그인 유저와 댓글을 작성한 유저의 일치 여부를 확인하기
        if(comment.getUsername() != user.getUsername() && user.getRole() != ADMIN) { // 작성자도 어드민도 아니면 댓글을 수정 할 수 없다.
            throw new CustomException(INVALID_USER); // 작성자와 유저가 일치하지 않을 때
        }
        comment.update(commentRequestDto);
        // DB에서 저장 된 댓글을 조회하여 반환하기
        return new CommentResponseDto(commentRepository.findById(comment.getId()).get());
    }

    @Transactional
    // 댓글 삭제하기
    public ResponseEntity deleteComment(Long id, String username) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND) // 댓글이 존재하지 않을 때
        );

        // 사용자 정보를 사용하여 Users의 인스턴스 가져오기
        User user = userRepository.findByUsername(username).get();

        //로그인 유저와 댓글을 작성한 유저의 일치 여부를 확인하기
        if(comment.getUsername() != user.getUsername() && user.getRole() != ADMIN) { // 작성자도 어드민도 아니면 댓글을 삭제 할 수 없다.
            throw new CustomException(INVALID_USER); // 작성자와 유저가 일치하지 않을 때
        }
        commentRepository.deleteById(id);
        return new ResponseEntity("댓글이 삭제되었습니다.", HttpStatus.OK);
    }

    @Transactional
    public void updateLikeByScheduler(Long id, Long cnt) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND)
        );
        comment.syncCommentLike(cnt);
    }
}
