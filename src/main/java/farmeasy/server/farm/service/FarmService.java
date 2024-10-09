package farmeasy.server.farm.service;

import farmeasy.server.user.domain.User;
import farmeasy.server.farm.dto.RegisterFarmReq;
import org.springframework.http.ResponseEntity;

public interface FarmService {
    ResponseEntity<RegisterFarmReq> createFarm(RegisterFarmReq req, User user);
}
