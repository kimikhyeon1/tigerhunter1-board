package com.sparta.tigercave.service;

import com.sparta.tigercave.entity.Post;
import com.sparta.tigercave.entity.PostLike;
import com.sparta.tigercave.repository.PostLikeRepository;
import com.sparta.tigercave.repository.PostRepository;
import com.sparta.tigercave.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UsersRepository userRepository;

    @Transactional
    public Long addLike(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("없는 게시물"));
        postLikeRepository.saveAndFlush(new PostLike(post));
        return postId;
    }

}
