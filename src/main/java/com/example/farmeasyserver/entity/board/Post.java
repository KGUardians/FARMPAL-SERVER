package com.example.farmeasyserver.entity.board;

import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@MappedSuperclass
@Data
public abstract class Post {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    @Column(name = "post_title",nullable = false)
    private String title;
    private LocalDateTime postedTime;
    private LocalDateTime updatedTime;
    private int postLike;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList;

    @PrePersist
    protected void onCreate(){
        postedTime = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedTime = LocalDateTime.now();
    }

    public void addImages(List<Image> added) { // 5
        added.stream().forEach(i -> {
            imageList.add(i);
            i.setPost(this);
        });
    }
}
