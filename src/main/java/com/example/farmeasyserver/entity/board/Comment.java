package com.example.farmeasyserver.entity.board;

import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    private LocalDateTime postedTime;
    private LocalDateTime updatedTime;

    @PrePersist
    protected void onCreate(){
        postedTime = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedTime = LocalDateTime.now();
    }

    public void setPost(Post post){
        this.post = post;
        post.getCommentList().add(this);
    }
}
