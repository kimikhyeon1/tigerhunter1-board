package com.sparta.tigercave.repository;

import com.sparta.tigercave.entity.Post;
import com.sparta.tigercave.entity.PostLike;
import com.sparta.tigercave.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, BigInteger> {
    List<PostLike> findByUserAndPost(User user,Post post);
}