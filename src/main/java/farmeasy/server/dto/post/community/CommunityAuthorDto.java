package farmeasy.server.dto.post.community;

import farmeasy.server.entity.user.Role;
import farmeasy.server.entity.user.User;
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
