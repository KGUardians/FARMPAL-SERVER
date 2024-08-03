package farmeasy.server.util;

import farmeasy.server.dto.mainpage.PostListDto;
import farmeasy.server.dto.post.community.CommunityListDto;
import farmeasy.server.dto.post.community.comment.CommentDto;
import farmeasy.server.entity.board.community.CommunityPost;
import farmeasy.server.entity.board.exprience.ExpApplication;
import farmeasy.server.entity.board.exprience.ExperiencePost;
import farmeasy.server.entity.board.market.MarketPost;
import farmeasy.server.entity.user.User;
import farmeasy.server.repository.post.community.CommentJpaRepo;
import farmeasy.server.repository.post.community.CommunityJpaRepo;
import farmeasy.server.repository.post.experience.ExpAppJpaRepo;
import farmeasy.server.repository.post.experience.ExpJpaRepo;
import farmeasy.server.repository.post.market.MarketJpaRepo;
import farmeasy.server.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostUtil {

    private final CommunityJpaRepo communityJpaRepo;
    private final ExpJpaRepo expJpaRepo;
    private final ExpAppJpaRepo expAppJpaRepo;
    private final MarketJpaRepo marketJpaRepo;
    private final CommentJpaRepo commentJpaRepo;

    public <T extends PostListDto> List<Long> extractPostIds(List<T> pageDto) {
        return pageDto.stream()
                .map(T::getPostId)
                .toList();
    }

    /*

    커뮤니티 게시글 관련

    */

    public CommunityPost getCommunityPost(Long postId){
        CommunityPost communityPost = communityJpaRepo.findByIdWithUser(postId)
                .orElseThrow(()-> new ResourceNotFoundException("CommunityPost", "communityPost", null));
        int viewCount = communityPost.getViewCount();
        communityPost.setViewCount(viewCount + 1);
        return communityPost;
    }

    public List<CommunityListDto> convertCommunityPostsToDtos(List<CommunityPost> recentCommunityPosts){
        return recentCommunityPosts.stream()
                .map(CommunityListDto::toDto)
                .toList();
    }

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


    /*

    마켓 게시글 관련

    */

    public MarketPost getMarketPost(Long postId){
        MarketPost marketPost = marketJpaRepo.findByIdWithUser(postId)
                .orElseThrow(() -> new ResourceNotFoundException("ExperiencePost", "experiencePost", null));
        marketPost.viewCountUp();

        return marketPost;
    }



    /*

    농촌체험 게시글 관련

    */
    public ExperiencePost getExperiencePost(Long postId){
        ExperiencePost experiencePost = expJpaRepo.findByIdWithUser(postId)
                .orElseThrow(() -> new ResourceNotFoundException("ExperiencePost", "experiencePost", null));
        experiencePost.viewCountUp();

        return experiencePost;
    }

    public void validateParticipants(ExperiencePost post, int participants) throws Exception {
        int availableNum = post.getRecruitment().getRecruitmentNum();
        if(availableNum < participants){
            throw new Exception("인원이 초과되었습니다.");
        }
    }

    public void processApplication(ExperiencePost post, User applicant, int participants){
        ExpApplication expApplication = new ExpApplication(participants,applicant,post);
        int remainingNum = post.getRecruitment().getRecruitmentNum() - participants;
        post.getRecruitment().setRecruitmentNum(remainingNum);
        expAppJpaRepo.save(expApplication);
    }
}
