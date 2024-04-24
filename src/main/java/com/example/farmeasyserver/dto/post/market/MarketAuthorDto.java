package com.example.farmeasyserver.dto.post.market;

import com.example.farmeasyserver.entity.user.Role;
import com.example.farmeasyserver.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MarketAuthorDto {
    private Long id;
    private String name;
    private Role role;

    public static MarketAuthorDto toDto(User author){
        return new MarketAuthorDto(
                author.getId(),
                author.getName(),
                author.getRole()
        );
    }

}
