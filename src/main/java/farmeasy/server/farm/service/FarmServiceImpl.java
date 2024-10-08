package farmeasy.server.farm.service;

import farmeasy.server.farm.domain.Farm;
import farmeasy.server.user.domain.User;
import farmeasy.server.farm.dto.RegisterFarmReq;
import farmeasy.server.user.repository.FarmJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmJpaRepo farmJpaRepo;

    @Override
    @Transactional
    public RegisterFarmReq createFarm(RegisterFarmReq req, User user) {
        Farm farm = RegisterFarmReq.toEntity(req);
        farm.setUser(user);
        farmJpaRepo.save(farm);
        return req;
    }
}
