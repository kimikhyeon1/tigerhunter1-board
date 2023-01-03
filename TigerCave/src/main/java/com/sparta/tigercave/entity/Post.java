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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Users users;


    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public Post(PostRequestDto requestDto, Users users) {
        this.users = users;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public Post(Users user, PostRequestDto postRequestDto) {
        this.users = user;
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }

    public void update(UpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public boolean isPostWriter(String username) {
        return username.equals(this.getUsers().getUsername());
    }


}
