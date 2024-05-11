package com.example.farmeasyserver.entity.board.exprience;

import com.example.farmeasyserver.entity.board.Post;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class ExperiencePost extends Post {
    @Embedded
    private Recruitment recruitment;

    @OneToMany(mappedBy = "experiencePost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExpApplication> application;

    public ExperiencePost(Recruitment recruitment) {
        this.recruitment = recruitment;
    }
}
