package farmeasy.server.dto.mainpage;

import farmeasy.server.dto.post.community.CommunityListDto;
import farmeasy.server.dto.post.experience.ExperienceListDto;
import farmeasy.server.dto.post.market.MarketListDto;
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
