package com.sparta.tigercave.entity;

import com.sparta.tigercave.dto.PostRequestDto;
import com.sparta.tigercave.dto.UpdateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped{
    /* iD가 2부터 저장되는 오류가 있습니다.strategy 속성의 값을
    GenerationType.AUTO에서 GenerationType.IDENTITY로 바꾸어주면 1부터 시작합니다. - 우시은*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;


    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> likes = new ArrayList<>();

    public int getCountOfLikes(){
        return this.likes.size();
    }


    public Post(PostRequestDto requestDto, User user) {
        this.user = user;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public Post(User user, PostRequestDto postRequestDto) {
        this.user = user;
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }

    public void update(UpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public boolean isPostWriter(String username) {
        return username.equals(this.getUser().getUsername());
    }


}
