package farmeasy.server.dto.post.experience;

import farmeasy.server.dto.user.FarmDto;
import farmeasy.server.entity.user.Role;
import farmeasy.server.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExperienceAuthorDto {
    private Long id;
    private String name;
    private Role role;
    private FarmDto farm;

    public static ExperienceAuthorDto toDto(User author){
        return new ExperienceAuthorDto(
                author.getId(),
                author.getName(),
                author.getRole(),
                FarmDto.toDto(author.getFarm())
        );
    }
}
