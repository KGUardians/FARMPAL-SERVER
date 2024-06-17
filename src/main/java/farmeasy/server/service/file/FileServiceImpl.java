package farmeasy.server.service.file;

import farmeasy.server.dto.ImageDto;
import farmeasy.server.dto.mainpage.PostListDto;
import farmeasy.server.repository.post.PostJpaRepo;
import farmeasy.server.util.PostUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final PostJpaRepo postJpaRepo;
    private final PostUtil postUtil;

    @Value(value = "${post.image.path}")
    String location;

    @PostConstruct
    void postConstruct() {
        File dir = new File(location);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void upload(MultipartFile file, String fileName) throws FileUploadException {
        try {
            file.transferTo(new File(location + fileName));
        } catch(IOException e) {
            throw new FileUploadException("파일을 업로드할 수 없습니다.");
        }
    }

    @Override
    public void delete(String fileName) {
        new File(location + fileName).delete();
    }

    public <T extends PostListDto> void imageMapping(List<T> mainPageDto){
        List<Long> postIdList = postUtil.extractPostIds(mainPageDto);
        List<ImageDto> postImages = fetchPostImages(postIdList);
        mapImageToPosts(mainPageDto,postImages);
    }

    private List<ImageDto> fetchPostImages(List<Long> postIdList){
        return postJpaRepo.findImagesDtoByPostIds(postIdList);
    }

    private <T extends PostListDto> void mapImageToPosts(List<T> postListDto, List<ImageDto> postImageList){
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



}
