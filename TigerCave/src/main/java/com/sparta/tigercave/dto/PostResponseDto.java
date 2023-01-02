package com.sparta.tigercave.dto;

import com.sparta.tigercave.entity.Post;
import lombok.Getter;
import lombok.Setter;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostResponseDto {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long id;
    private String title;
    private String username;
    private String contents;
    private List<CommentResponseDto> commentList;

    public PostResponseDto(Post post) {
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.contents = post.getContents();

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : post.getCommentList()) {
            commentResponseDtoList.add(new CommentResponseDto(comment));
        }
        this.commentList = commentResponseDtoList;
    }
}