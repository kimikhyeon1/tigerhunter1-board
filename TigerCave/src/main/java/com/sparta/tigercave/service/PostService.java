package com.sparta.tigercave.service;

import com.sparta.tigercave.dto.*;
import com.sparta.tigercave.entity.Comment;
import com.sparta.tigercave.entity.Post;
import com.sparta.tigercave.entity.Users;
import com.sparta.tigercave.exception.CustomException;
import com.sparta.tigercave.repository.PostRepository;
import com.sparta.tigercave.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.tigercave.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UsersRepository usersRepository;
    private final PostRepository blogRepository;
    private final UsersRepository userRepository;

    public PostResponseDto createPost(PostRequestDto requestDto, String username) {
        Users users = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(USER_NOT_FOUND)
        );
        Post post = new Post(requestDto, users);
        blogRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        List<Post> postList = blogRepository.getAllByOrderByModifiedAtDesc();
        List<PostResponseDto> responseDtoList = postList.stream().map(post -> new PostResponseDto(post)).collect(Collectors.toList());
        return responseDtoList;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long id) {
        Post post = blogRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        return new PostResponseDto(post);
    }

//    @Transactional(readOnly = true)
//    public List<PostResponseDto> getPostByUsername(UserNameRequestDto requestDto) {
//        String username = requestDto.getUsername();
//        List<Post> postList = blogRepository.findByUserUsername(username);
//        return postList.stream().map(post -> new PostResponseDto(post)).collect(Collectors.toList());
//    }


    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, UserDetails userDetails) {



        // userDetails에서 가져온 사용자 정보를 사용하여 Users의 인스턴스 가져오기
        Users user = usersRepository.findByUsername(userDetails.getUsername()).get();

        // DB에 댓글을 저장하기
        Post post = blogRepository.saveAndFlush(new Post(user, postRequestDto));

        // DB에서 저장 된 댓글을 조회하여 반환하기
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto update(Long id, UpdateRequestDto requestDto, String username) {
        Post post = blogRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        if (!post.isPostWriter(username)) {
            throw new CustomException(INVALID_USER);
        }
        post.update(requestDto);
        return new PostResponseDto(post);
    }

    public PostResponseDto updateAdmin(Long id, UpdateRequestDto requestDto) {
        Post post = blogRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        post.update(requestDto);
        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long id, String username) {
        Post post = blogRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        if (!post.isPostWriter(username)) {
            throw new CustomException(INVALID_USER);
        }
        blogRepository.deleteById(id);
    }

    public void deletePostAdmin(Long id) {
        Post post = blogRepository.findById(id).orElseThrow(
                () -> new CustomException(POST_NOT_FOUND)
        );
        blogRepository.deleteById(id);
    }
}