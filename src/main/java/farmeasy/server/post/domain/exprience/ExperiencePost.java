package farmeasy.server.post.domain.exprience;

import farmeasy.server.post.domain.Post;
import farmeasy.server.post.domain.PostType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class ExperiencePost extends Post {
    @Embedded
    private Recruitment recruitment;

    @OneToMany(mappedBy = "experiencePost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExpApplication> application;

    public ExperiencePost(){
        super(PostType.EXPERIENCE);
    }

    public ExperiencePost(Recruitment recruitment) {
        super(PostType.EXPERIENCE);
        this.recruitment = recruitment;
    }
}
