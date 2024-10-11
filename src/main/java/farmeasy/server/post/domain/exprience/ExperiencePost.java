package farmeasy.server.post.domain.exprience;

import farmeasy.server.post.domain.Post;
import farmeasy.server.post.domain.PostType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
public class ExperiencePost extends Post {
    @Embedded
    private Recruitment recruitment;

    @OneToMany(mappedBy = "experiencePost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExpApplication> applications = new ArrayList<>();

    public ExperiencePost(){
        super(PostType.EXPERIENCE);
    }

    public ExperiencePost(Recruitment recruitment) {
        super(PostType.EXPERIENCE);
        this.recruitment = recruitment;
    }

    public void addApplication(ExpApplication application) {
        applications.add(application);
    }

    public boolean validateParticipants(int participants){
        return recruitment.getRemainingCapacity() < participants;
    }

    public void removeApplication(ExpApplication application) {
        applications.remove(application);
        recruitment.increaseCapacity(application.getParticipants());
    }

    public boolean canAccommodate(int additionalParticipants) {
        return recruitment.getRemainingCapacity() >= additionalParticipants;
    }
}
