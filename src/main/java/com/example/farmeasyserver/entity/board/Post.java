package com.example.farmeasyserver.entity.board;

import com.example.farmeasyserver.dto.post.ImageUpdateResult;
import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Entity
@NoArgsConstructor
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    @Column(name = "post_title",nullable = false)
    private String title;
    @Enumerated(EnumType.STRING)
    private CropCategory cropCategory;
    @Lob
    private String content;
    private LocalDateTime postedTime;
    private LocalDateTime updatedTime;
    private int postLike;
    @Enumerated(EnumType.STRING)
    private PostType postType;
    private int viewCount;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        postedTime = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedTime = LocalDateTime.now();
    }

    public void addImages(List<MultipartFile> imageFileList) {
        List<Image> i_list = imageFileList.stream().map(i -> new Image(i.getOriginalFilename())).toList();
        i_list.stream().forEach(i -> {
            imageList.add(i);
            i.setPost(this);
        });
    }
    private void deleteImageList(List<Image> deleted){
        deleted.stream().forEach(d -> this.imageList.remove(d));
    }


    public void setAuthor(User author){
        this.author = author;
        author.getPostList().add(this);
    }

    public void viewCountUp(Post post){
        post.viewCount++;
    }

    @Getter
    @AllArgsConstructor
    public static class ImageUpdatedResult { // 4
        private List<MultipartFile> addedImageFiles;
        private List<Image> addedImages;
        private List<Image> deletedImages;
    }
}
