package com.example.farmeasyserver.util.post;

import com.example.farmeasyserver.dto.mainpage.PostListDto;
import com.example.farmeasyserver.entity.board.community.CommunityPost;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import com.example.farmeasyserver.entity.board.market.MarketPost;
import com.example.farmeasyserver.repository.post.community.CommunityJpaRepo;
import com.example.farmeasyserver.repository.post.experience.ExpJpaRepo;
import com.example.farmeasyserver.repository.post.market.MarketJpaRepo;
import com.example.farmeasyserver.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostUtil {

    private final CommunityJpaRepo communityJpaRepo;
    private final ExpJpaRepo expJpaRepo;
    private final MarketJpaRepo marketJpaRepo;

    public <T extends PostListDto> List<Long> extractPostIds(List<T> pageDto){
        return pageDto.stream()
                .map(T::getPostId)
                .toList();
    }

    public CommunityPost getCommunityPost(Long postId){
        return communityJpaRepo.findByIdWithUser(postId).orElseThrow(()-> new ResourceNotFoundException("CommunityPost", "communityPost", null));
    }

    public ExperiencePost getExperiencePost(Long postId){
        return expJpaRepo.findByIdWithUser(postId).orElseThrow(() -> new ResourceNotFoundException("ExperiencePost", "experiencePost", null));
    }
    public MarketPost getMarketPost(Long postId){
        return marketJpaRepo.findByIdWithUser(postId).orElseThrow(() -> new ResourceNotFoundException("ExperiencePost", "experiencePost", null));
    }

}
