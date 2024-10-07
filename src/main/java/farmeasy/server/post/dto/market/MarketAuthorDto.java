package farmeasy.server.post.dto.market;

import farmeasy.server.user.dto.FarmDto;
import farmeasy.server.user.domain.Role;
import farmeasy.server.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
