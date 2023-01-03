package com.sparta.tigercave.repository;

import com.sparta.tigercave.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getAllByOrderByModifiedAtDesc();
    List<Post> findByUserUsername(String username);
}
