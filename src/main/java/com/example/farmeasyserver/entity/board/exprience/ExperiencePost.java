package com.example.farmeasyserver.entity.board.exprience;

import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.ItemCategory;
import com.example.farmeasyserver.entity.board.community.CommunityType;
import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ExperiencePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    @Column(name = "post_title",nullable = false)
    private String title;
    @Embedded
    private Recruitment recruitment;
    @Enumerated
    private ItemCategory crop;
    private String farmName;
    private int postLike;
    private LocalDateTime postedTime;
    private LocalDateTime updatedTime;
    @OneToMany(mappedBy = "e_post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList;

    public ExperiencePost(User author, String title, Recruitment recruitment, List<Image> imageList) {
        this.author = author;
        this.title = title;
        this.recruitment = recruitment;
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
