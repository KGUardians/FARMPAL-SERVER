package farmeasy.server.file.service;

import farmeasy.server.file.domain.Image;
import farmeasy.server.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ImageManager {

    private final FileService fileService;

    public List<Image> convertImageFileToImage(List<MultipartFile> imageFileList){
        return imageFileList.stream()
                .map(i -> new Image(i.getOriginalFilename()))
                .toList();
    }

    public List<Image> convertImageIdsToImages(Post post, List<Long> imageIds) {
        return imageIds.stream()
                .map(id -> findImageById(post, id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<Image> findImageById(Post post, Long id) {
        return post.getImageList().stream()
                .filter(image -> image.getId().equals(id))
                .findFirst();
    }

    public void addImageList(Post post, List<Image> imageList) {
        imageList.forEach(i -> {
            post.getImageList().add(i);
            i.setPost(post);
        });
    }

    public void deleteImageList(Post post, List<Image> imagesToDelete) {
        imagesToDelete.forEach(post.getImageList()::remove);
    }

    public void uploadImages(List<Image> images, List<MultipartFile> fileImages) {
        IntStream.range(0, images.size()).forEach(i -> {
            try {
                fileService.upload(fileImages.get(i), images.get(i).getUniqueName());
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteImages(List<Image> images){
        images.stream().forEach(i -> fileService.delete(i.getUniqueName()));
    }
}
