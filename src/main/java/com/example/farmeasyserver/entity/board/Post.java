package com.example.farmeasyserver.entity.board;

import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "post_type")
@Data
public abstract class Post {
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

    public void setAuthor(User author){
        this.author = author;
        author.getPosts().add(this);
    }

}
