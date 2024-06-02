package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.ImageDto;
import com.example.farmeasyserver.dto.post.*;
import com.example.farmeasyserver.dto.mainpage.PostListDto;
import com.example.farmeasyserver.entity.board.Image;
import com.example.farmeasyserver.entity.board.Post;
import com.example.farmeasyserver.entity.user.Role;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.UserJpaRepo;
import com.example.farmeasyserver.repository.post.PostJpaRepo;
import com.example.farmeasyserver.service.file.FileService;
import com.example.farmeasyserver.util.exception.user.UserException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class PostService {
    private final UserJpaRepo userJpaRepo;
    private final PostJpaRepo postJpaRepo;
    private final FileService fileService;

    /*

    리스트 이미지 매핑

    */
    <T extends PostListDto> void imageMapping(List<T> mainPageDto){
        List<Long> postIdList = extractPostIds(mainPageDto);
        List<ImageDto> postImages = fetchPostImages(postIdList);
        mapImageToPosts(mainPageDto,postImages);
    }
    <T extends PostListDto> List<Long> extractPostIds(List<T> pageDto){
        return pageDto.stream()
                .map(T::getPostId)
                .toList();
    }
    private List<ImageDto> fetchPostImages(List<Long> postIdList){
        return postJpaRepo.findImagesDtoByPostIds(postIdList);
    }

    private  <T extends PostListDto> void mapImageToPosts(List<T> postListDto, List<ImageDto> postImageList){
        Map<Long, List<ImageDto>> imagesByPostId = groupImagesByPostId(postImageList);
        postListDto.forEach(p -> {
            List<ImageDto> imageDtoList = imagesByPostId.get(p.getPostId());
            if (imageDtoList != null && !imageDtoList.isEmpty()) {
                p.setImage(imageDtoList.get(0));
            }
        });
    }
    private Map<Long, List<ImageDto>> groupImagesByPostId(List<ImageDto> postImageList) {
        return postImageList.stream()
                .collect(Collectors.groupingBy(ImageDto::getPostId));
    }


    <T extends Post> T createPost(T p, CreatePostRequest req, User author) {
        p.createPostFromReq(req, author);
        uploadImages(p.getImageList(),req.getImageList());
        return p;
    }

    void deletePost(Post post, User author){
        checkUser(author,post.getAuthor().getId());
        deleteImages(post.getImageList());
        postJpaRepo.delete(post);
    }

    void updatePost(User author, Post post, UpdatePostRequest req){
        checkUser(author,post.getAuthor().getId());
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

    private boolean isAuthorized(User user, Long authorId){
        return user.getRole().equals(Role.ADMIN) || user.getId().equals(authorId);
    }

    private void checkUser(User user, Long authorId){
        if(!isAuthorized(user,authorId)) throw new UserException("해당 권한이 없습니다.", HttpStatus.BAD_REQUEST);
    }

    User getUser(Long userId){
        return userJpaRepo.findById(userId).orElseThrow();
    }

}
