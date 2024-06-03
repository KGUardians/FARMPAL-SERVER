package com.example.farmeasyserver.util.post;

import com.example.farmeasyserver.dto.mainpage.PostListDto;
import com.example.farmeasyserver.dto.post.community.CommunityListDto;
import com.example.farmeasyserver.dto.post.community.comment.CommentDto;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.board.exprience.ExpApplication;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.community.CommentJpaRepo;
import com.example.farmeasyserver.repository.post.community.CommunityJpaRepo;
import com.example.farmeasyserver.repository.post.experience.ExpAppJpaRepo;
import com.example.farmeasyserver.repository.post.experience.ExpJpaRepo;
import com.example.farmeasyserver.repository.post.market.MarketJpaRepo;
import com.example.farmeasyserver.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        return communityJpaRepo.findByIdWithUser(postId).orElseThrow(()-> new ResourceNotFoundException("CommunityPost", "communityPost", null));
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
        return marketJpaRepo.findByIdWithUser(postId).orElseThrow(() -> new ResourceNotFoundException("ExperiencePost", "experiencePost", null));
    }



    /*

    농촌체험 게시글 관련

    */
    public ExperiencePost getExperiencePost(Long postId){
        return expJpaRepo.findByIdWithUser(postId).orElseThrow(() -> new ResourceNotFoundException("ExperiencePost", "experiencePost", null));
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
