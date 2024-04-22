package com.example.farmeasyserver.entity.board.community;

import com.example.farmeasyserver.entity.board.Comment;
import com.example.farmeasyserver.entity.board.Image;
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
public class CommunityPost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)

    private User author;
    @Column(name = "post_title",nullable = false)
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name = "community_type")
    private CommunityType communityType;
    @Lob
    private String content;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();
    private LocalDateTime postedTime;
    private LocalDateTime updatedTime;
    private int postLike;
    @OneToMany(mappedBy = "c_post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList;

    public CommunityPost(User author, String title, CommunityType type, String content, List<Image> imageList) {
        this.author = author;
        this.title = title;
        this.communityType = type;
        this.content = content;
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
}
