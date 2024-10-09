package farmeasy.server.post.service.experience;

import farmeasy.server.post.domain.exprience.ExperiencePost;
import farmeasy.server.post.dto.CreatePostResponse;
import farmeasy.server.post.dto.experience.CreateExpPostRequest;
import farmeasy.server.post.dto.experience.ExperienceListDto;
import farmeasy.server.post.dto.experience.ExperiencePostDto;
import farmeasy.server.post.dto.experience.UpdateExpPostReq;
import farmeasy.server.user.domain.User;
import farmeasy.server.post.repository.experience.ExpFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExperiencePostService {

    List<ExperienceListDto> getRecentExperiencePostDtos();

    ResponseEntity<CreatePostResponse> createExperiencePost(CreateExpPostRequest req, User user);

    ResponseEntity<Void> deleteExperiencePost(Long postId, User user);

    ResponseEntity<ExperiencePostDto> readExperiencePost(Long postId);

    ResponseEntity<ExperiencePostDto> updateExperiencePost(Long postId, UpdateExpPostReq req, User user);

    ResponseEntity<Slice<ExperienceListDto>> getExperiencePosts(ExpFilter filter, Pageable pageable);

    ExperiencePost getExperiencePost(Long postId);
}
