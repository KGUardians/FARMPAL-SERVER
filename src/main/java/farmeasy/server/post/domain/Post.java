package farmeasy.server.post.domain;

import farmeasy.server.file.domain.Image;
import farmeasy.server.file.service.ImageManager;
import farmeasy.server.post.dto.CreatePostRequest;
import farmeasy.server.post.dto.UpdateImageResult;
import farmeasy.server.post.dto.UpdatePostRequest;
import farmeasy.server.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    public Post(PostType postType){
        this.postType = postType;
    }

    @PrePersist
    protected void onCreate(){
        postedTime = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedTime = LocalDateTime.now();
    }

    public void createPostFromReq(CreatePostRequest req, User author, ImageManager imageManager){
        title = req.getTitle();
        content = req.getContent();
        cropCategory = req.getCropCategory();
        setAuthor(author);
        imageManager.addImageList(this, imageManager.convertImageFileToImage(req.getImageList()));
    }

    public UpdateImageResult updatePostFromReq(UpdatePostRequest req, ImageManager imageManager) { // 1
        title = req.getTitle();
        content = req.getContent();
        cropCategory = req.getCropCategory();

        List<Image> addedImages = imageManager.convertImageFileToImage(req.getAddedImages());
        List<Image> deletedImages = imageManager.convertImageIdsToImages(this, req.getDeletedImages());

        imageManager.addImageList(this, addedImages);
        imageManager.deleteImageList(this, deletedImages);
        return new UpdateImageResult(req.getAddedImages(), addedImages, deletedImages);
    }

    private void setAuthor(User author){
        this.author = author;
        author.addPost(this);
    }

    public void increaseViewCount(){
        this.setViewCount(this.getViewCount() + 1);
    }

    public void increaseLikes(){
        this.postLike++;
    }

    public void decreaseLikes(){
        this.postLike--;
    }
}
