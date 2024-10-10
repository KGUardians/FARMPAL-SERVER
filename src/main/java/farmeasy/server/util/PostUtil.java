package farmeasy.server.util;

import farmeasy.server.main.dto.PostListDto;
import farmeasy.server.post.domain.Post;
import farmeasy.server.post.dto.community.CommunityListDto;
import farmeasy.server.comment.dto.CommentDto;
import farmeasy.server.post.repository.PostJpaRepo;
import farmeasy.server.comment.repository.CommentJpaRepo;
import farmeasy.server.util.exception.ResourceNotFoundException;
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

    //todo 게시글 댓글 서비스 만들고 옮기기
    public void commentMapping(List<CommunityListDto> mainPageDto){
        List<Long> postIds = extractPostIds(mainPageDto);
        List<CommentDto> comments = fetchCommentDtoByPostIds(postIds);
        mapCommentCountToPosts(mainPageDto, comments);
    }

    private void mapCommentCountToPosts(List<CommunityListDto> postListDto, List<CommentDto> postCommentList){
        Map<Long, List<CommentDto>> imagesByPostId = groupCommentByPostId(postCommentList);
        postListDto.forEach(p -> {
            List<CommentDto> CommentDtoList = imagesByPostId.get(p.getPostId());
            if (CommentDtoList != null && !CommentDtoList.isEmpty()) {
                p.setCommentCount(CommentDtoList.size());
            }
        });
    }

    private List<CommentDto> fetchCommentDtoByPostIds(List<Long> postIds){
        return commentJpaRepo.findCommentDtoListByPostIds(postIds);
    }

    private Map<Long, List<CommentDto>> groupCommentByPostId(List<CommentDto> postCommentList) {
        return postCommentList.stream()
                .collect(Collectors.groupingBy(CommentDto::getPostId));
    }

}
