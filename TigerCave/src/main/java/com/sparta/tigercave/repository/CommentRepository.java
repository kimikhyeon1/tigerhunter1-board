package com.sparta.tigercave.repository;

import com.sparta.tigercave.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
