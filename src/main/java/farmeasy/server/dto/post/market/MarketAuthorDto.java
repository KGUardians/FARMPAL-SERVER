package farmeasy.server.dto.post.market;

import farmeasy.server.dto.user.FarmDto;
import farmeasy.server.entity.user.Role;
import farmeasy.server.entity.user.User;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class MarketAuthorDto {
    private Long id;
    private String name;
    private Role role;
    private FarmDto farm;

    public static MarketAuthorDto toDto(User author){
        return MarketAuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .role(author.getRole())
                .farm(FarmDto.toDto(author.getFarm()))
                .build();
    }

}
