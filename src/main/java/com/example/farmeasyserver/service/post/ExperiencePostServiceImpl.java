package com.example.farmeasyserver.service.post;

import com.example.farmeasyserver.dto.post.CreatePostResponse;
import com.example.farmeasyserver.dto.post.experience.*;
import com.example.farmeasyserver.dto.post.experience.expapplication.*;
import com.example.farmeasyserver.entity.board.exprience.ExpApplication;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.experience.*;
import com.example.farmeasyserver.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperiencePostServiceImpl implements ExperiencePostService{

    private final ExpJpaRepo expJpaRepo;
    private final ExpRepo expRepo;
    private final PostServiceImpl postService;
    private final ExpAppJpaRepo expAppJpaRepo;

    @Override
    public List<ExperienceListDto> getRecentExperiencePostDtos() {
        List<ExperienceListDto> recentExperiencePosts = expRepo.findTop4OrderByIdDesc();
        postService.imageMapping(recentExperiencePosts); return recentExperiencePosts;
    }

    @Override
    @Transactional
    public CreatePostResponse createExperiencePost(CreateExpPostRequest req, User author) {
        ExperiencePost experiencePost = postService.createPost(CreateExpPostRequest.toEntity(req),req, author);
        expJpaRepo.save(experiencePost);
        return new CreatePostResponse(experiencePost.getId(),experiencePost.getPostType());
    }

    @Override
    @Transactional
    public Long deleteExperiencePost(Long postId, User author) {
        ExperiencePost post = getExperiencePost(postId);
        postService.deletePost(post,author);
        return postId;
    }

    @Override
    public ExperiencePostDto readExperiencePost(Long postId){
        return ExperiencePostDto.toDto(getExperiencePost(postId));
    }

    @Override
    public ExperiencePostDto updateExperiencePost(Long postId, UpdateExpPostReq req, User author) {
        ExperiencePost post = getExperiencePost(postId);
        postService.updatePost(author, post, req);
        post.setRecruitment(UpdateExpPostReq.reqToRecruitment(req));
        expJpaRepo.save(post);
        return ExperiencePostDto.toDto(post);
    }

    @Override
    public Slice<ExperienceListDto> getExperiencePosts(ExpFilter filter, Pageable pageable) {
        Slice<ExperienceListDto> listResponse = expRepo.findPostList(filter,pageable);
        postService.imageMapping(listResponse.stream().toList()); return listResponse;
    }

    @Override
    public ExpApplicationPageDto getExpAppPage(Long postId) {
        ExperiencePost post = getExperiencePost(postId);
        return ExpApplicationPageDto.toDto(post);
    }

    @Override
    @Transactional
    public ExpApplicationRequest requestExpApp(Long postId, ExpApplicationRequest req, User user) throws Exception {
        ExperiencePost experiencePost = getExperiencePost(postId);
        validateParticipants(experiencePost, req.getParticipants());
        User applicant = postService.getUser(user.getId());
        processApplication(experiencePost, applicant, req.getParticipants());
        return req;
    }

    private void validateParticipants(ExperiencePost post, int participants) throws Exception {
        int availableNum = post.getRecruitment().getRecruitmentNum();
        if(availableNum < participants){
            throw new Exception("인원이 초과되었습니다.");
        }
    }

    private void processApplication(ExperiencePost post, User applicant, int participants){
        ExpApplication expApplication = new ExpApplication(participants,applicant,post);
        int remainingNum = post.getRecruitment().getRecruitmentNum() - participants;
        post.getRecruitment().setRecruitmentNum(remainingNum);
        expAppJpaRepo.save(expApplication);
    }

    private ExperiencePost getExperiencePost(Long postId){
        return expJpaRepo.findByIdWithUser(postId).orElseThrow(() -> new ResourceNotFoundException("ExperiencePost", "experiencePost", null));
    }
}
