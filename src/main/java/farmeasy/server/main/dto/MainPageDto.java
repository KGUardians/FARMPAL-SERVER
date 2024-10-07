package farmeasy.server.main.dto;

import farmeasy.server.post.dto.community.CommunityListDto;
import farmeasy.server.post.dto.experience.ExperienceListDto;
import farmeasy.server.post.dto.market.MarketListDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class MainPageDto {
    List<CommunityListDto> listCommunityDtoList;
    List<MarketListDto> listMarketDtoList;
    List<ExperienceListDto> listExperienceDtoList;

}
