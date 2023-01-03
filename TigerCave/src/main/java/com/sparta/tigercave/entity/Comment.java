package com.sparta.tigercave.entity;

import com.sparta.tigercave.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@Entity
@DynamicInsert
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // FK(외래키)로 post_id를 갖습니다.
    @ManyToOne
    @JoinColumn(name ="post_id")
    Post post;

    // FK(외래키)로 user_id를 갖습니다.
    @ManyToOne
    @JoinColumn(name ="users_id")
    Users user;
    @Column
    String username;
    @Column
    String comment;
    // 댓글의 좋아요는 0부터 시작
    @ColumnDefault("0")
    @Column(nullable = false)
    Long comment_likes;

    public Comment(Users user, Post post, CommentRequestDto commentRequestDto) {
        this.post = post;
        this.user = user;
        this.username = user.getUsername();
        this.comment = commentRequestDto.getComment();
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }
}
