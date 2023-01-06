package com.sparta.tigercave.service;

import com.sparta.tigercave.entity.Comment;
import com.sparta.tigercave.entity.CommentLike;
import com.sparta.tigercave.entity.User;
import com.sparta.tigercave.exception.CustomException;
import com.sparta.tigercave.repository.CommentLikeRepository;
import com.sparta.tigercave.repository.CommentRepository;
import com.sparta.tigercave.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import static com.sparta.tigercave.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final UserRepository userRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public boolean addLike(Long commentId, Long userId) {
        //사용자 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND)
        );

        //댓글 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND)
        );

        //사용자의 좋아요 상태 체크
        Optional<CommentLike> isExistCommentLike = commentLikeRepository.findByUserAndComment(user, comment);

        //아직 좋아요를 안 한 사용자일 경우
        if (isExistCommentLike.isEmpty()) {
            commentLikeRepository.save(new CommentLike(user, comment));
            comment.addLike();
            return true;
        }

        //이미 좋아요를 한 사용자일 경우
        return false;
    }

    @Transactional
    public boolean deleteLike(Long commentId, Long userId) {
        //사용자 확인
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND)
        );

        //댓글 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(COMMENT_NOT_FOUND)
        );

        //사용자의 좋아요 상태 체크
        Optional<CommentLike> isExistCommentLike = commentLikeRepository.findByUserAndComment(user, comment);

        //아직 좋아요를 안 한 사용자일 경우
        if (isExistCommentLike.isPresent()) {
            commentLikeRepository.deleteById(isExistCommentLike.get().getId());
            comment.deleteLike();
            return true;
        }

        //이미 좋아요를 한 사용자일 경우
        return false;
    }

}
