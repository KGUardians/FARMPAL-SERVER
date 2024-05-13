package com.example.farmeasyserver.entity.board.community;

import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
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
    private CommunityPost post;
    private LocalDateTime postedTime;
    private LocalDateTime updatedTime;

    public Comment(String comment){
        this.comment = comment;
    }

    @PrePersist
    protected void onCreate(){
        postedTime = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedTime = LocalDateTime.now();
    }

    public void setPost(CommunityPost post){
        this.post = post;
        post.getCommentList().add(this);
    }

    public void setAuthor(User author){
        this.author = author;
        author.getComments().add(this);
    }
}