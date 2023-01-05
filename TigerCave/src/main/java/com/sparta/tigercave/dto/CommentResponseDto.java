package com.sparta.tigercave.dto;

import com.sparta.tigercave.entity.Comment;
import com.sparta.tigercave.entity.Timestamped;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String username;
    private String comment;
    private Long comment_likes;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.createAt = comment.getCreateAt(); // post.getCreatedAt(); -> post.getCreateAt(); 으로 수정 했습니다.
        this.modifiedAt = comment.getModifiedAt();
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
        this.comment_likes = comment.getComment_likes();
    }
}
