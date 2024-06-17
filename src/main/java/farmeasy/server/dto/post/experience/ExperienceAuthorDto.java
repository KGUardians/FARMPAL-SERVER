package farmeasy.server.dto.post.experience;

import farmeasy.server.dto.user.FarmDto;
import farmeasy.server.entity.user.Role;
import farmeasy.server.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExperienceAuthorDto {
    private Long id;
    private String name;
    private Role role;
    private FarmDto farm;

    public static ExperienceAuthorDto toDto(User author){
        return ExperienceAuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .role(author.getRole())
                .farm(FarmDto.toDto(author.getFarm()))
                .build();
    }
}
