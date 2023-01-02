package com.sparta.tigercave.dto;

import com.sparta.tigercave.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;
    private String comment;
    private Long comment_likes;
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.comment_likes = comment.getComment_likes();
    }
}
