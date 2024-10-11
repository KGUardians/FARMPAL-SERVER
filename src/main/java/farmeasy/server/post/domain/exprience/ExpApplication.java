package farmeasy.server.post.domain.exprience;

import farmeasy.server.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = {"experiencePost","applicants"})
public class ExpApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ExperiencePost experiencePost;

    @ManyToOne(fetch = FetchType.LAZY)
    private User applicants;

    private int participants;

    public ExpApplication(int participants, User applicant, ExperiencePost post){
        this.participants = participants;
        setApplicants(applicant);
        setPost(post);
    }
    public void setApplicants(User applicants){
        this.applicants = applicants;
        applicants.addExpApplication(this);
    }

    public void setPost(ExperiencePost experiencePost){
        this.experiencePost = experiencePost;
        experiencePost.addApplication(this);
    }
}
