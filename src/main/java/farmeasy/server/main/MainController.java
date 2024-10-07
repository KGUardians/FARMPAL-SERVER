package farmeasy.server.main;

import farmeasy.server.main.dto.MainPageDto;
import farmeasy.server.dto.response.Response;
import farmeasy.server.post.service.community.CommunityPostService;
import farmeasy.server.post.service.experience.ExperiencePostService;
import farmeasy.server.post.service.market.MarketPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final CommunityPostService communityPostService;
    private final MarketPostService marketPostService;
    private final ExperiencePostService experiencePostService;

    @GetMapping
    public Response mainPage(){
        MainPageDto mainPageDto = new MainPageDto(
                communityPostService.getRecentCommunityPostDtos(),
                marketPostService.getRecentMarketPostDtos(),
                experiencePostService.getRecentExperiencePostDtos());

        return Response.success(mainPageDto);
    }
}
