package farmeasy.server.entity.board;

import farmeasy.server.dto.post.CreatePostRequest;
import farmeasy.server.dto.post.UpdateImageResult;
import farmeasy.server.dto.post.UpdatePostRequest;
import farmeasy.server.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    private List<Image> convertImageFileToImage(List<MultipartFile> imageFileList){
        return imageFileList.stream().map(i -> new Image(i.getOriginalFilename())).toList();
    }
    private void addImageList(List<Image> imageList) {
        imageList.forEach(i -> {
            this.imageList.add(i);
            i.setPost(this);
        });
    }
    public void createPostFromReq(CreatePostRequest req, User author){
        this.title = req.getTitle();
        this.content = req.getContent();
        this.cropCategory = req.getCropCategory();
        this.setAuthor(author);
        this.addImageList(convertImageFileToImage(req.getImageList()));
    }

    public UpdateImageResult updatePostFromReq(UpdatePostRequest req) { // 1
        this.title = req.getTitle();
        this.content = req.getContent();
        this.cropCategory = req.getCropCategory();
        UpdateImageResult result = new UpdateImageResult(req.getAddedImages(),
                convertImageFileToImage(req.getAddedImages()),
                convertImageIdsToImages(req.getDeletedImages()));
        addImageList(result.getAddedImageList());
        deleteImageList(result.getDeletedImageList());
        return result;
    }

    private List<Image> convertImageIdsToImages(List<Long> imageIds) {
        return imageIds.stream()
                .map(this::convertImageIdToImage)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<Image> convertImageIdToImage(Long id) {
        return this.imageList.stream().filter(i -> i.getId().equals(id)).findAny();
    }

    private void deleteImageList(List<Image> deleted) {
        deleted.stream().forEach(di -> this.imageList.remove(di));
    }

    private void setAuthor(User author){
        this.author = author;
        author.getPostList().add(this);
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
