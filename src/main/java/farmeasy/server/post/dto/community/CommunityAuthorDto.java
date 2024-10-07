package farmeasy.server.post.dto.community;

import farmeasy.server.user.domain.Role;
import farmeasy.server.user.domain.User;
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
