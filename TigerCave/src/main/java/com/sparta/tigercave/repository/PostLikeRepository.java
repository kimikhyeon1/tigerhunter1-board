package com.sparta.tigercave.repository;

import com.sparta.tigercave.entity.Post;
import com.sparta.tigercave.entity.PostLike;
import com.sparta.tigercave.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
//    Optional<PostLike> findByUserAndBoard(User user, Post post);
//    Optional<PostLike> findByUserId(User user);
}