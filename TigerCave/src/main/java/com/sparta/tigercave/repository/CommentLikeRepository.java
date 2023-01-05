package com.sparta.tigercave.repository;

import com.sparta.tigercave.entity.Comment;
import com.sparta.tigercave.entity.CommentLike;
import com.sparta.tigercave.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
    Long countById(Long Id);
}
