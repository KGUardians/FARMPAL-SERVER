package farmeasy.server.post.service;

import farmeasy.server.file.service.ImageManager;
import farmeasy.server.post.domain.Post;
import farmeasy.server.post.dto.CreatePostRequest;
import farmeasy.server.post.dto.UpdateImageResult;
import farmeasy.server.post.dto.UpdatePostRequest;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.PostJpaRepo;
import farmeasy.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostJpaRepo postJpaRepo;
    private final UserService userService;
    private final ImageManager imageManager;

    public <T extends Post> T createPost(T p, CreatePostRequest req, User author) {
        p.createPostFromReq(req, author, imageManager);
        imageManager.uploadImages(p.getImageList(),req.getImageList());
        return p;
    }

    public void deletePost(Post post, User author){
        userService.checkUser(author,post.getAuthor().getId());
        imageManager.deleteImages(post.getImageList());
        postJpaRepo.delete(post);
    }

    public void updatePost(User author, Post post, UpdatePostRequest req){
        userService.checkUser(author,post.getAuthor().getId());
        UpdateImageResult result = post.updatePostFromReq(req, imageManager);
        imageManager.deleteImages(result.getDeletedImageList());
        imageManager.uploadImages(result.getAddedImageList(),result.getAddedImageFileList());
    }
}
