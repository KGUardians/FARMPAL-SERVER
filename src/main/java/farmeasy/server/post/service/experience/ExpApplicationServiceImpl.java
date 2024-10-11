package farmeasy.server.post.service.experience;

import farmeasy.server.post.domain.exprience.ExpApplication;
import farmeasy.server.post.domain.exprience.ExperiencePost;
import farmeasy.server.post.dto.experience.expapplication.ExpApplicationPageDto;
import farmeasy.server.post.dto.experience.expapplication.ExpApplicationRequest;
import farmeasy.server.post.repository.experience.ExpAppJpaRepo;
import farmeasy.server.user.domain.User;
import farmeasy.server.util.exception.post.experience.MaxParticipantsExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpApplicationServiceImpl implements ExpApplicationService {

    private final ExpAppJpaRepo expAppJpaRepo;
    private final ExperiencePostService experiencePostService;

    @Override
    public ExpApplicationPageDto getExpAppPage(Long postId) {
        ExperiencePost post = experiencePostService.getExperiencePost(postId);
        return ExpApplicationPageDto.toDto(post);
    }

    @Override
    @Transactional
    public ExpApplicationRequest requestExpApp(Long postId, ExpApplicationRequest req, User applicant) throws Exception {
        ExperiencePost experiencePost = experiencePostService.getExperiencePost(postId);
        checkParticipantNum(experiencePost, req.getParticipants());
        processApplication(experiencePost, applicant, req.getParticipants());
        return req;
    }

    private void checkParticipantNum(ExperiencePost post, int participants) {
        if(post.validateParticipants(participants)){
            throw new MaxParticipantsExceededException("인원이 초과되었습니다.");
        }
    }

    private void processApplication(ExperiencePost post, User applicant, int participants){
        ExpApplication expApplication = new ExpApplication(participants,applicant,post);
        post.getRecruitment().reduceCapacity(participants);
        expAppJpaRepo.save(expApplication);
    }
}
