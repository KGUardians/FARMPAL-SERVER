package com.example.farmeasyserver.entity.board.exprience;

import com.example.farmeasyserver.entity.board.Post;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class ExperiencePost extends Post {
    @Embedded
    private Recruitment recruitment;

    public ExperiencePost(Recruitment recruitment) {
        this.recruitment = recruitment;
    }
}
