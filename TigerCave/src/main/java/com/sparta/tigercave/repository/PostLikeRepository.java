package com.sparta.tigercave.repository;

import com.sparta.tigercave.entity.Post;
import com.sparta.tigercave.entity.PostLike;
import com.sparta.tigercave.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findByUserAndPost(User user,Post post);
}