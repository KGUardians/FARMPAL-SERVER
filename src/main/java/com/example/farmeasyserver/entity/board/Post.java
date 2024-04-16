package com.example.farmeasyserver.entity.board;

import com.example.farmeasyserver.entity.board.item.Item;
import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Column(nullable = false)
    private User author;
    @Column(name = "post_title",nullable = false)
    private String title;
    @Lob
    private String content;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;
    private LocalDateTime postedTime;
    private LocalDateTime updatedTime;
    private int like;
    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Item item;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList;

    //CommunityPost Request
    public Post(User author, String title, PostType postType, List<Image> imageList) {
        this.author = author;
        this.title = title;
        this.postType = postType;
        this.imageList = imageList;
    }

    //MarketPost Request
    public Post(PostType postType,String title,String content,Item item,User user,List<Image> imageList){
        this.postType = postType;
        this.title = title;
        this.content = content;
        this.item = item;
        this.author = user;
        this.imageList = imageList;
    }

    //RuralExpPost Request
    public Post(PostType postType,String title,String content,User user,List<Image> imageList){
        this.postType = postType;
        this.title = title;
        this.content = content;
        this.author = user;
        this.imageList = imageList;
    }

    @PrePersist
    protected void onCreate(){
        postedTime = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedTime = LocalDateTime.now();
    }

    public void setAuthor(User author){
        this.author = author;
        author.getPosts().add(this);
    }

}
