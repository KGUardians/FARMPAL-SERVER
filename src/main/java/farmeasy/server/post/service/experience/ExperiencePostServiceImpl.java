package farmeasy.server.post.service.experience;

import farmeasy.server.post.domain.exprience.ExperiencePost;
import farmeasy.server.post.dto.CreatePostResponse;
import farmeasy.server.post.dto.experience.CreateExpPostRequest;
import farmeasy.server.post.dto.experience.ExperienceListDto;
import farmeasy.server.post.dto.experience.ExperiencePostDto;
import farmeasy.server.post.dto.experience.UpdateExpPostReq;
import farmeasy.server.post.service.ImageMappingService;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.experience.ExpFilter;
import farmeasy.server.post.repository.experience.ExpJpaRepo;
import farmeasy.server.post.repository.experience.ExpRepo;
import farmeasy.server.post.service.PostService;
import farmeasy.server.util.exception.ResourceNotFoundException;
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
    private final ImageMappingService imageMappingService;

    @Override
    public List<ExperienceListDto> getRecentExperiencePostDtos() {
        List<ExperienceListDto> recentExperiencePosts = expRepo.findTop4OrderByIdDesc();
        imageMappingService.imageMapping(recentExperiencePosts); return recentExperiencePosts;
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
    @Transactional
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
        Slice<ExperienceListDto> listResponse = expRepo.findPostList(filter, pageable);
        imageMappingService.imageMapping(listResponse.stream().toList());
        return listResponse;
    }

    @Override
    public ExperiencePost getExperiencePost(Long postId){
        ExperiencePost experiencePost = expJpaRepo.findByIdWithUser(postId)
                .orElseThrow(() -> new ResourceNotFoundException("ExperiencePost", "experiencePost", null));
        experiencePost.increaseViewCount();

        return experiencePost;
    }

}
