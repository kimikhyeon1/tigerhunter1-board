package com.sparta.tigercave.entity;

import com.sparta.tigercave.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // FK(외래키)로 post_id를 갖습니다.
    @ManyToOne
    @JoinColumn(name ="post_id")
    Post post;

//    FK(외래키)로 user_id를 갖습니다.
//    @ManyToOne
//    @JoinColumn(name ="user_id")
//    User user;
//    @Column
//    String username;
    @Column
    String comment;
    // 댓글의 좋아요는 0부터 시작
    @Column
    Long comment_likes = 0L;

    public Comment(Post post, CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
        this.post = post;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }
}
