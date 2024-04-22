package com.example.farmeasyserver.dto.post.experience;

import com.example.farmeasyserver.dto.post.PostDto;
import com.example.farmeasyserver.entity.board.exprience.ExperiencePost;
import com.example.farmeasyserver.entity.board.exprience.Recruitment;
import lombok.Data;

@Data
public class ExperiencePostDto extends PostDto {
    private Recruitment recruitment;
    public static ExperiencePostDto toDto(ExperiencePost post){
        return new ExperiencePostDto();
    }
}
