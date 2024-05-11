package com.example.farmeasyserver.entity.board.exprience;

import com.example.farmeasyserver.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ExpApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ExperiencePost experiencePost;

    @ManyToOne(fetch = FetchType.LAZY)
    private User applicants;

    private int participants;

    public void setApplicants(User applicants){
        this.applicants = applicants;
        applicants.getExpApplications().add(this);
    }

    public void setPost(ExperiencePost experiencePost){
        this.experiencePost = experiencePost;
        experiencePost.getApplication().add(this);
    }
}