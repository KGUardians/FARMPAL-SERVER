package farmeasy.server.controller;

import farmeasy.server.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pest-inspection")
public class PestInspectionController {

    @PostMapping
    @Operation(summary = "병해충 검사 요청")
    public Response Inspection(){
        return Response.success();
    }


}
