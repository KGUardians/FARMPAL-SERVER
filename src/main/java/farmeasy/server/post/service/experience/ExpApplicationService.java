package farmeasy.server.post.service.experience;

import farmeasy.server.post.dto.experience.expapplication.ExpApplicationPageDto;
import farmeasy.server.post.dto.experience.expapplication.ExpApplicationRequest;
import farmeasy.server.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ExpApplicationService {
    ResponseEntity<ExpApplicationRequest> requestExpApp(Long postId, ExpApplicationRequest req, User applicant) throws Exception;
    ResponseEntity<ExpApplicationPageDto> getExpAppPage(Long postId);
}
