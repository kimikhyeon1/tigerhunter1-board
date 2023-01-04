package com.sparta.tigercave.service;

import com.sparta.tigercave.entity.Post;
import com.sparta.tigercave.entity.PostLike;
import com.sparta.tigercave.entity.User;
import com.sparta.tigercave.repository.PostLikeRepository;
import com.sparta.tigercave.repository.PostRepository;
import com.sparta.tigercave.repository.UserRepository;
import com.sparta.tigercave.security.UserDetailImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Transactional
    public Long addLike(Long postId, UserDetailImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("없는 게시물"));
        User user = userRepository.findById(userDetails.getUserId()).get();
        List<PostLike> postLikes  = postLikeRepository.findByUserAndPost(user,post);
        if (postLikes.isEmpty()){
            postLikeRepository.save(new PostLike(post,user));
            return postId;
        }
        postLikeRepository.delete(postLikes.get(0));
        return postId;
    }
}
