package com.sparta.tigercave.service;

import com.sparta.tigercave.dto.PostRequestDto;
import com.sparta.tigercave.dto.PostResponseDto;
import com.sparta.tigercave.dto.UpdateRequestDto;
import com.sparta.tigercave.dto.UserNameRequestDto;
import com.sparta.tigercave.entity.Post;
import com.sparta.tigercave.entity.Users;
import com.sparta.tigercave.repository.PostRepository;
import com.sparta.tigercave.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository blogRepository;
    private final UsersRepository usersRepository;

    public PostResponseDto createPost(PostRequestDto requestDto, String username) {
        Users users = usersRepository.findByUsername(username).orElseThrow(
                () -> new CustomException("아이디가 존재하지 않습니다.")
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
                () -> new CustomException("존재하지 않는 포스팅입니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostByUsername(UserNameRequestDto requestDto) {
        String username = requestDto.getUsername();
        List<Post> postList = blogRepository.findByUserUsername(username);
        return postList.stream().map(post -> new PostResponseDto(post)).collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto update(Long id, UpdateRequestDto requestDto, String username) {
        Post post = blogRepository.findById(id).orElseThrow(
                () -> new CustomException("존재하지 않는 포스팅입니다.")
        );
        if (!post.isPostWriter(username)) {
            throw new CustomException("본인이 작성한 게시글만 수정할 수 있습니다.");
        }
        post.update(requestDto);
        return new PostResponseDto(post);
    }

    public PostResponseDto updateAdmin(Long id, UpdateRequestDto requestDto) {
        Post post = blogRepository.findById(id).orElseThrow(
                () -> new CustomException("존재하지 않는 포스팅입니다.")
        );
        post.update(requestDto);
        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long id, String username) {
        Post post = blogRepository.findById(id).orElseThrow(
                () -> new CustomException("존재하지 않는 포스팅입니다.")
        );
        if (!post.isPostWriter(username)) {
            throw new CustomException("본인이 작성한 게시글만 삭제할 수 있습니다.");
        }
        blogRepository.deleteById(id);
    }

    public void deletePostAdmin(Long id) {
        Post post = blogRepository.findById(id).orElseThrow(
                () -> new CustomException("존재하지 않는 포스팅입니다.")
        );
        blogRepository.deleteById(id);
    }
}
