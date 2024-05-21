package com.example.farmeasyserver.dto.user;

import com.example.farmeasyserver.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenDto {
    private String username;
    private String token;

    public static UserTokenDto toDto(User user, String token) {
        return new UserTokenDto(user.getUsername(), token);
    }
}
