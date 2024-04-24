package com.example.farmeasyserver.dto.post.community;

import com.example.farmeasyserver.entity.user.Role;
import com.example.farmeasyserver.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommunityAuthorDto {
    private Long id;
    private String name;
    private Role role;

    public static CommunityAuthorDto toDto(User author){
        return new CommunityAuthorDto(
                author.getId(),
                author.getName(),
                author.getRole()
        );
    }

}
