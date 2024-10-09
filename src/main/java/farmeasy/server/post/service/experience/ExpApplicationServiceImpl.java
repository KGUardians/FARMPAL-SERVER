package farmeasy.server.post.service.experience;

import farmeasy.server.post.domain.exprience.ExpApplication;
import farmeasy.server.post.domain.exprience.ExperiencePost;
import farmeasy.server.post.dto.experience.expapplication.ExpApplicationPageDto;
import farmeasy.server.post.dto.experience.expapplication.ExpApplicationRequest;
import farmeasy.server.post.repository.experience.ExpAppJpaRepo;
import farmeasy.server.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpApplicationServiceImpl implements ExpApplicationService {

    private final ExpAppJpaRepo expAppJpaRepo;
    private final ExperiencePostService experiencePostService;

    @Override
    public ResponseEntity<ExpApplicationPageDto> getExpAppPage(Long postId) {
        ExperiencePost post = experiencePostService.getExperiencePost(postId);
        return ResponseEntity.ok(ExpApplicationPageDto.toDto(post));
    }

    @Override
    @Transactional
    public ResponseEntity<ExpApplicationRequest> requestExpApp(Long postId, ExpApplicationRequest req, User applicant) throws Exception {
        ExperiencePost experiencePost = experiencePostService.getExperiencePost(postId);
        checkParticipantNum(experiencePost, req.getParticipants());
        processApplication(experiencePost, applicant, req.getParticipants());
        return ResponseEntity.status(HttpStatus.CREATED).body(req);
    }

    private void checkParticipantNum(ExperiencePost post, int participants) throws Exception {
        if(post.validateParticipants(participants)){
            throw new Exception("인원이 초과되었습니다.");
        }
    }

    private void processApplication(ExperiencePost post, User applicant, int participants){
        ExpApplication expApplication = new ExpApplication(participants,applicant,post);
        int remainingNum = post.getRecruitment().getRecruitmentNum() - participants;
        post.getRecruitment().setRecruitmentNum(remainingNum);
        expAppJpaRepo.save(expApplication);
    }
}
