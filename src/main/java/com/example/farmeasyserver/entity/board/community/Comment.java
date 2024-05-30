package com.example.farmeasyserver.entity.board.community;

import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"author","post"})
public class Comment {
    @Id
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

    public Comment(String comment,CommunityPost post, User author){
        this.comment = comment;
        setPost(post);
        setAuthor(author);
    }

    @PrePersist
    protected void onCreate(){
        postedTime = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedTime = LocalDateTime.now();
    }

    private void setPost(CommunityPost post){
        this.post = post;
        post.getCommentList().add(this);
    }

    private void setAuthor(User author){
        this.author = author;
        author.getComments().add(this);
    }
}
