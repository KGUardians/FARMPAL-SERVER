package farmeasy.server.post.service;

import farmeasy.server.file.domain.Image;
import farmeasy.server.post.domain.Post;
import farmeasy.server.post.dto.CreatePostRequest;
import farmeasy.server.post.dto.UpdateImageResult;
import farmeasy.server.post.dto.UpdatePostRequest;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.PostJpaRepo;
import farmeasy.server.file.service.FileService;
import farmeasy.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostJpaRepo postJpaRepo;
    private final FileService fileService;
    private final UserService userService;

    public <T extends Post> T createPost(T p, CreatePostRequest req, User author) {
        p.createPostFromReq(req, author);
        uploadImages(p.getImageList(),req.getImageList());
        System.out.println("hi");
        return p;
    }

    public void deletePost(Post post, User author){
        userService.checkUser(author,post.getAuthor().getId());
        deleteImages(post.getImageList());
        postJpaRepo.delete(post);
    }

    public void updatePost(User author, Post post, UpdatePostRequest req){
        userService.checkUser(author,post.getAuthor().getId());
        UpdateImageResult result = post.updatePostFromReq(req);
        deleteImages(result.getDeletedImageList());
        uploadImages(result.getAddedImageList(),result.getAddedImageFileList());
    }

    private void uploadImages(List<Image> images, List<MultipartFile> fileImages) {
        IntStream.range(0, images.size()).forEach(i -> {
            try {
                fileService.upload(fileImages.get(i), images.get(i).getUniqueName());
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void deleteImages(List<Image> images){
        images.stream().forEach(i -> fileService.delete(i.getUniqueName()));
    }


}
