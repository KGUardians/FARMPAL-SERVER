package farmeasy.server.service.post.experience;

import farmeasy.server.dto.post.CreatePostResponse;
import farmeasy.server.dto.post.experience.CreateExpPostRequest;
import farmeasy.server.dto.post.experience.ExperienceListDto;
import farmeasy.server.dto.post.experience.ExperiencePostDto;
import farmeasy.server.dto.post.experience.UpdateExpPostReq;
import farmeasy.server.dto.post.experience.expapplication.ExpApplicationPageDto;
import farmeasy.server.dto.post.experience.expapplication.ExpApplicationRequest;
import farmeasy.server.entity.user.User;
import farmeasy.server.repository.post.experience.ExpFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ExperiencePostService {

    List<ExperienceListDto> getRecentExperiencePostDtos();

    CreatePostResponse createExperiencePost(CreateExpPostRequest req, User user);

    Long deleteExperiencePost(Long postId, User user);

    ExperiencePostDto readExperiencePost(Long postId);

    ExperiencePostDto updateExperiencePost(Long postId, UpdateExpPostReq req, User user);

    Slice<ExperienceListDto> getExperiencePosts(ExpFilter filter, Pageable pageable);

    ExpApplicationPageDto getExpAppPage(Long postId);

    ExpApplicationRequest requestExpApp(Long postId, ExpApplicationRequest req, User applicants) throws Exception;

}
