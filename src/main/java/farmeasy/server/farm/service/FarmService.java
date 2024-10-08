package farmeasy.server.farm.service;

import farmeasy.server.user.domain.User;
import farmeasy.server.farm.dto.RegisterFarmReq;

public interface FarmService {
    RegisterFarmReq createFarm(RegisterFarmReq req, User user);
}
