package farmeasy.server.post.service;

import farmeasy.server.file.dto.ImageDto;
import farmeasy.server.main.dto.PostListDto;
import farmeasy.server.post.repository.PostJpaRepo;
import farmeasy.server.util.PostUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageMappingServiceImpl implements ImageMappingService {

    private final PostUtil postUtil;
    private final PostJpaRepo postJpaRepo;

    public <T extends PostListDto> void imageMapping(List<T> mainPageDto){
        List<Long> postIdList = postUtil.extractPostIds(mainPageDto);
        List<ImageDto> postImages = fetchPostImages(postIdList);
        mapImageToPosts(mainPageDto,postImages);
    }

    private <T extends PostListDto> void mapImageToPosts(List<T> postListDto, List<ImageDto> postImageList) {
        Map<Long, List<ImageDto>> imagesByPostId = groupImagesByPostId(postImageList);
        postListDto.forEach(post -> {
            List<ImageDto> imageDtoList = imagesByPostId.get(post.getPostId());
            if (imageDtoList != null && !imageDtoList.isEmpty()) {
                post.setImage(imageDtoList.get(0));
            }
        });
    }

    private List<ImageDto> fetchPostImages(List<Long> postIdList){
        return postJpaRepo.findImagesDtoByPostIds(postIdList);
    }

    private Map<Long, List<ImageDto>> groupImagesByPostId(List<ImageDto> postImageList) {
        return postImageList.stream()
                .collect(Collectors.groupingBy(ImageDto::getPostId));
    }
}
