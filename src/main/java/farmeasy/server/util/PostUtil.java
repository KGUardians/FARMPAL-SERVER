package farmeasy.server.util;

import farmeasy.server.main.dto.PostListDto;
import farmeasy.server.post.dto.community.CommunityListDto;
import farmeasy.server.comment.dto.CommentDto;
import farmeasy.server.comment.repository.CommentJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostUtil {

    private final CommentJpaRepo commentJpaRepo;

    public List<Long> extractPostIds(List<? extends PostListDto> pageDto) {
        return pageDto.stream()
                .map(PostListDto::getPostId)
                .toList();
    }

    public void commentMapping(List<CommunityListDto> mainPageDto){
        List<Long> postIds = extractPostIds(mainPageDto);
        List<CommentDto> comments = commentJpaRepo.findCommentDtoListByPostIds(postIds);
        mapCommentCountToPosts(mainPageDto, comments);
    }

    private void mapCommentCountToPosts(List<CommunityListDto> postListDto, List<CommentDto> postCommentList){
        Map<Long, List<CommentDto>> imagesByPostId = postCommentList.stream()
                .collect(Collectors.groupingBy(CommentDto::getPostId));

        postListDto.forEach(p -> {
            List<CommentDto> CommentDtoList = imagesByPostId.get(p.getPostId());
            if (CommentDtoList != null && !CommentDtoList.isEmpty()) {
                p.setCommentCount(CommentDtoList.size());
            }
        });
    }

}
