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
        // 게시글 존재 여부 체크
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("없는 게시물"));
        // userDetails에서 userId 체크
        User user = userRepository.findById(userDetails.getUserId()).get();
        // 게시글에 유저좋아요 기록이 있는지 확인
        List<PostLike> postLikes  = postLikeRepository.findByUserAndPost(user,post);
        // 기록이 없다면 저장
        if (postLikes.isEmpty()){
            postLikeRepository.save(new PostLike(post,user));
            return postId;
        }
        // 기록이 있다면 삭제
        postLikeRepository.delete(postLikes.get(0));
        return postId;
    }
}
