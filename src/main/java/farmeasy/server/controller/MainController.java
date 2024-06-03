package farmeasy.server.controller;

import farmeasy.server.dto.mainpage.MainPageDto;
import farmeasy.server.dto.response.Response;
import farmeasy.server.service.post.community.CommunityPostService;
import farmeasy.server.service.post.experience.ExperiencePostService;
import farmeasy.server.service.post.market.MarketPostService;
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
