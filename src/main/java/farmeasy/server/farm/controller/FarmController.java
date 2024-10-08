package farmeasy.server.farm.controller;

import farmeasy.server.dto.response.Response;
import farmeasy.server.user.domain.User;
import farmeasy.server.farm.dto.RegisterFarmReq;
import farmeasy.server.farm.service.FarmService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class FarmController {

    private final FarmService farmService;

    @PostMapping("/farm")
    @Operation(summary = "농장 등록 요청")
    public Response registerFarm(@RequestBody RegisterFarmReq req, @AuthenticationPrincipal User user){
        return Response.success(farmService.createFarm(req, user));
    }
}
