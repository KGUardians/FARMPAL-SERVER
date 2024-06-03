package farmeasy.server.dto.post.market;

import farmeasy.server.dto.user.FarmDto;
import farmeasy.server.entity.user.Role;
import farmeasy.server.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MarketAuthorDto {
    private Long id;
    private String name;
    private Role role;
    private FarmDto farm;

    public static MarketAuthorDto toDto(User author){
        return new MarketAuthorDto(
                author.getId(),
                author.getName(),
                author.getRole(),
                FarmDto.toDto(author.getFarm())
        );
    }

}
