package com.sparta.tigercave.entity;

import com.sparta.tigercave.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@DynamicInsert
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // FK(외래키)로 postId를 갖습니다.
    @ManyToOne
    @JoinColumn(name ="postId")
    Post post;

    // FK(외래키)로 userId를 갖습니다.
    @ManyToOne
    @JoinColumn(name ="userId")
    User user;
    @Column
    String username;
    @Column
    String comment;

    // 댓글의 좋아요는 0부터 시작
    @ColumnDefault("0")
    @Column(nullable = false)
    Long comment_likes;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLike> likes = new ArrayList<>();

    public Comment(User user, Post post, CommentRequestDto commentRequestDto) {
        this.post = post;
        this.user = user;
        this.username = user.getUsername();
        this.comment = commentRequestDto.getComment();
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }

    public void addLike() {
        this.comment_likes += 1;
    }

    public void deleteLike() {
        this.comment_likes -= 1;
    }

    public void syncCommentLike(Long commentLikeCnt) { this.comment_likes = commentLikeCnt; }
}
