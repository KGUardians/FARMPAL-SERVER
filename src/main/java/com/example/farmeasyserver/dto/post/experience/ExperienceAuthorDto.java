package com.example.farmeasyserver.dto.post.experience;

import com.example.farmeasyserver.entity.user.Role;
import com.example.farmeasyserver.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExperienceAuthorDto {
    private Long id;
    private String name;
    private Role role;

    public static ExperienceAuthorDto toDto(User author){
        return new ExperienceAuthorDto(
                author.getId(),
                author.getName(),
                author.getRole()
        );
    }

}
