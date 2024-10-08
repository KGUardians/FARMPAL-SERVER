package farmeasy.server.farm.service;

import farmeasy.server.user.domain.User;
import farmeasy.server.user.dto.RegisterFarmReq;

public interface FarmService {
    RegisterFarmReq createFarm(RegisterFarmReq req, User user);
}
