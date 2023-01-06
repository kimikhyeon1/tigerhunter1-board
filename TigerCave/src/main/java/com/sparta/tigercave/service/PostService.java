package com.sparta.tigercave.service;

import com.sparta.tigercave.dto.*;
import com.sparta.tigercave.entity.Post;
import com.sparta.tigercave.entity.User;
import com.sparta.tigercave.exception.CustomException;
import com.sparta.tigercave.repository.PostRepository;
import com.sparta.tigercave.repository.UserRepository;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.tigercave.entity.UserRoleEnum.ADMIN;
import static com.sparta.tigercave.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        if(Collections.isEmpty(postList)) return null;
        List<PostResponseDto> postresponseDtoList = postList.stream().map(post -> new PostResponseDto(post)).collect(Collectors.toList());
        return postresponseDtoList;
    }

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, UserDetails userDetails) {

        // userDetails에서 가져온 사용자 정보를 사용하여 Users의 인스턴스 가져오기
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        
        // DB에 게시글을 저장하기
        Post post = postRepository.saveAndFlush(new Post(user, postRequestDto));

        // DB에서 저장 된 게시글을 조회하여 반환하기
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, UserDetails userDetails) {
        // 게시글 존재 여부 확인
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        // 작성자이거나 관리자인지 확인
        if(post.getUsername() != user.getUsername() && user.getRole() != ADMIN) {
            throw new CustomException(INVALID_USER);
        }
        post.update(postRequestDto);

        return new PostResponseDto(postRepository.findById(post.getId()).get());
    }

    

    @Transactional
    public ResponseEntity deletePost(Long id, UserDetails userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        User user = userRepository.findByUsername(userDetails.getUsername()).get();

        //로그인한 유저와 게시글을 작성한 유저의 일치 여부를 확인하기
        if(post.getUsername() != user.getUsername() && user.getRole() != ADMIN) { // 작성자도 어드민도 아니면 게시글을 삭제 할 수 없다.
            throw new CustomException(INVALID_USER); // 작성자와 유저가 일치하지 않을 때
        }
        postRepository.deleteById(id);
        return new ResponseEntity("게시글이 삭제되었습니다.", HttpStatus.OK);
    }

}