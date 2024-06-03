package com.example.farmeasyserver.service.post.experience;

import com.example.farmeasyserver.dto.post.CreatePostResponse;
import com.example.farmeasyserver.dto.post.experience.*;
import com.example.farmeasyserver.dto.post.experience.expapplication.*;
import com.example.farmeasyserver.entity.board.exprience.ExpApplication;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.repository.post.experience.*;
import com.example.farmeasyserver.service.file.FileService;
import com.example.farmeasyserver.service.post.PostService;
import com.example.farmeasyserver.util.post.PostUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperiencePostServiceImpl implements ExperiencePostService {

    private final ExpJpaRepo expJpaRepo;
    private final ExpRepo expRepo;
    private final PostService postService;
    private final FileService fileService;
    private final ExpAppJpaRepo expAppJpaRepo;
    private final PostUtil postUtil;

    @Override
    public List<ExperienceListDto> getRecentExperiencePostDtos() {
        List<ExperienceListDto> recentExperiencePosts = expRepo.findTop4OrderByIdDesc();
        fileService.imageMapping(recentExperiencePosts); return recentExperiencePosts;
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
        ExperiencePost post = postUtil.getExperiencePost(postId);
        postService.deletePost(post,author);
        return postId;
    }

    @Override
    public ExperiencePostDto readExperiencePost(Long postId){
        return ExperiencePostDto.toDto(postUtil.getExperiencePost(postId));
    }

    @Override
    public ExperiencePostDto updateExperiencePost(Long postId, UpdateExpPostReq req, User author) {
        ExperiencePost post = postUtil.getExperiencePost(postId);
        postService.updatePost(author, post, req);
        post.setRecruitment(UpdateExpPostReq.reqToRecruitment(req));
        expJpaRepo.save(post);
        return ExperiencePostDto.toDto(post);
    }

    @Override
    public Slice<ExperienceListDto> getExperiencePosts(ExpFilter filter, Pageable pageable) {
        Slice<ExperienceListDto> listResponse = expRepo.findPostList(filter,pageable);
        fileService.imageMapping(listResponse.stream().toList()); return listResponse;
    }

    @Override
    public ExpApplicationPageDto getExpAppPage(Long postId) {
        ExperiencePost post = postUtil.getExperiencePost(postId);
        return ExpApplicationPageDto.toDto(post);
    }

    @Override
    @Transactional
    public ExpApplicationRequest requestExpApp(Long postId, ExpApplicationRequest req, User applicant) throws Exception {
        ExperiencePost experiencePost = postUtil.getExperiencePost(postId);
        postUtil.validateParticipants(experiencePost, req.getParticipants());
        postUtil.processApplication(experiencePost, applicant, req.getParticipants());
        return req;
    }


}
