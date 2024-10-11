package farmeasy.server.post.service.experience;

import farmeasy.server.post.domain.exprience.ExpApplication;
import farmeasy.server.post.domain.exprience.ExperiencePost;
import farmeasy.server.post.dto.experience.expapplication.ExpApplicationPageDto;
import farmeasy.server.post.dto.experience.expapplication.ExpApplicationRequest;
import farmeasy.server.post.repository.experience.ExpAppJpaRepo;
import farmeasy.server.user.domain.User;
import farmeasy.server.util.exception.post.experience.ApplicationNotFoundException;
import farmeasy.server.util.exception.post.experience.MaxParticipantsExceededException;
import jakarta.validation.Valid;
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
    public void deleteExpApp(Long appId, User applicant) {
        ExpApplication expApp = getExpApp(appId);

        ExperiencePost experiencePost = expApp.getExperiencePost();
        experiencePost.removeApplication(expApp);

        expAppJpaRepo.delete(expApp);
    }

    @Override
    @Transactional
    public void updateExpApp(Long appId, @Valid ExpApplicationRequest req, User applicant) {
        ExpApplication expApp = getExpApp(appId);

        ExperiencePost experiencePost = expApp.getExperiencePost();
        int oldParticipants = expApp.getParticipants();
        int newParticipants = req.getParticipants();

        int changeParticipants = newParticipants - oldParticipants;

        if(!experiencePost.canAccommodate(changeParticipants)) {
            throw new MaxParticipantsExceededException("변경하려는 인원이 모집 가능 인원을 초과했습니다.");
        }

        experiencePost.getRecruitment().reduceCapacity(changeParticipants);
        expApp.setParticipants(newParticipants);
        expAppJpaRepo.save(expApp);
    }

    @Override
    @Transactional
    public ExpApplicationRequest requestExpApp(Long postId, @Valid ExpApplicationRequest req, User applicant) {
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

    private void processApplication(ExperiencePost post, User applicant, int participants) {
        ExpApplication expApplication = new ExpApplication(participants,applicant,post);
        post.getRecruitment().reduceCapacity(participants);
        expAppJpaRepo.save(expApplication);
    }

    private ExpApplication getExpApp(Long appId){
        return expAppJpaRepo.findById(appId)
                .orElseThrow(() -> new ApplicationNotFoundException("해당 신청이 존재하지 않습니다."));

    }
}
