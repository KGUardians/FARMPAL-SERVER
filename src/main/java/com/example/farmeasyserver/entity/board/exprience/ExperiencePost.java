package com.example.farmeasyserver.entity.board.exprience;

import com.example.farmeasyserver.entity.board.CropCategory;
import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ExperiencePost extends Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    @Embedded
    private Recruitment recruitment;
    @Enumerated
    private CropCategory cropCategory;
    private String farmName;
    @OneToMany(mappedBy = "e_post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList;

    public ExperiencePost(String title, Recruitment recruitment) {
        this.title = title;
        this.recruitment = recruitment;
    }

    public void addImages(List<Image> added) {
        added.stream().forEach(i -> {
            imageList.add(i);
            i.setPost(this);
        });
    }

    public void setAuthor(User author){
        this.author = author;
        author.getExperiencePosts().add(this);
    }
}
