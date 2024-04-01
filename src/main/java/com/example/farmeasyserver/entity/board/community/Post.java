package com.example.farmeasyserver.entity.board.community;

import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;
    @Column(name = "post_title")
    private String title;
    @Lob
    private String content;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();
    private Timestamp postedTime;
    private Timestamp updatedTime;
    @Enumerated
    @Column(name = "board_type")
    private BoardType type;

    @PrePersist
    protected void onCreate(){
        postedTime = new Timestamp(System.currentTimeMillis());
    }
    @PreUpdate
    protected void onUpdate(){
        updatedTime = new Timestamp(System.currentTimeMillis());
    }

}
