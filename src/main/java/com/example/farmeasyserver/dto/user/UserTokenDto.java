package com.example.farmeasyserver.dto.user;

import com.example.farmeasyserver.entity.user.Role;
import com.example.farmeasyserver.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserTokenDto {
    private String username;
    private String token;


    @Builder
    public UserTokenDto(String username, String token){
        this.username = username;
        this.token = token;
    }
    public static UserTokenDto fromEntity(User user, String token) {
        return UserTokenDto.builder()
                .username(user.getUsername())
                .token(token)
                .build();
    }
}
