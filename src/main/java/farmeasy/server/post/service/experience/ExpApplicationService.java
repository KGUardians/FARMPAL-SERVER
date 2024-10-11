package farmeasy.server.post.service.experience;

import farmeasy.server.post.dto.experience.expapplication.ExpApplicationPageDto;
import farmeasy.server.post.dto.experience.expapplication.ExpApplicationRequest;
import farmeasy.server.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface ExpApplicationService {
    ExpApplicationRequest requestExpApp(Long postId, ExpApplicationRequest req, User applicant) throws Exception;
    ExpApplicationPageDto getExpAppPage(Long postId);
    void deleteExpApp(Long appId, User applicant);
    void updateExpApp(Long appId, ExpApplicationRequest expApplicationRequest, User applicant) throws IllegalAccessException;
}
