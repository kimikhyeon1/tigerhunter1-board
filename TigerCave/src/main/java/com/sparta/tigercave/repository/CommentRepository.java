package com.sparta.tigercave.repository;

import com.sparta.tigercave.entity.Comment;
import com.sparta.tigercave.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long id);
}
