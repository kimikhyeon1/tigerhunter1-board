package com.sparta.tigercave.service;

import com.sparta.tigercave.dto.PostRequestDto;
import com.sparta.tigercave.dto.PostResponseDto;
import com.sparta.tigercave.dto.UpdateRequestDto;
import com.sparta.tigercave.dto.UserNameRequestDto;
import com.sparta.tigercave.entity.Post;
import com.sparta.tigercave.entity.Post;
import com.sparta.tigercave.entity.User;
import com.sparta.tigercave.repository.PostRepository;
import com.sparta.tigercave.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository blogRepository;
    private final UserRepository userRepository;

    public PostResponseDto createPost(PostRequestDto requestDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        Post post = new Post(requestDto, user);
        blogRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        List<Post> postingList = blogRepository.getAllByOrderByModifiedAtDesc();
        List<PostResponseDto> responseDtoList = postingList.stream().map(posting -> new PostResponseDto(posting)).collect(Collectors.toList());
        return responseDtoList;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long id) {
        Post posting = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스팅입니다.")
        );
        return new PostResponseDto(posting);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostByUsername(UserNameRequestDto requestDto) {
        String username = requestDto.getUsername();
        List<Post> postingList = blogRepository.findByUserUsername(username);
        return postingList.stream().map(posting -> new PostResponseDto(posting)).collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto update(Long id, UpdateRequestDto requestDto, String username) {
        Post posting = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스팅입니다.")
        );
        if (!posting.isPostWriter(username)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.");
        }
        posting.update(requestDto);
        return new PostResponseDto(posting);
    }

    public PostResponseDto updateAdmin(Long id, UpdateRequestDto requestDto) {
        Post posting = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스팅입니다.")
        );
        posting.update(requestDto);
        return new PostResponseDto(posting);
    }

    @Transactional
    public void deletePost(Long id, String username) {
        Post posting = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스팅입니다.")
        );
        if (!posting.isPostWriter(username)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 삭제할 수 있습니다.");
        }
        blogRepository.deleteById(id);
    }

    public void deletePostAdmin(Long id) {
        Post posting = blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 포스팅입니다.")
        );
        blogRepository.deleteById(id);
    }
}
