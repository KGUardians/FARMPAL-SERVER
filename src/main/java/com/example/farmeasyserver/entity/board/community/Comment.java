package com.example.farmeasyserver.entity.board.community;

import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

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
    private Timestamp postedTime;
    private Timestamp updatedTime;

    @PrePersist
    protected void onCreate(){
        postedTime = new Timestamp(System.currentTimeMillis());
    }
    @PreUpdate
    protected void onUpdate(){
        updatedTime = new Timestamp(System.currentTimeMillis());
    }
}
