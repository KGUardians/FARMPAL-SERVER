package farmeasy.server.dto.post.community;

import farmeasy.server.entity.user.Role;
import farmeasy.server.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CommunityAuthorDto {
    private Long id;
    private String name;
    private Role role;

    public static CommunityAuthorDto toDto(User author){
        return CommunityAuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .role(author.getRole())
                .build();
    }

}
