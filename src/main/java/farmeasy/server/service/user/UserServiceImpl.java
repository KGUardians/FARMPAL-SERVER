package farmeasy.server.service.user;

import farmeasy.server.config.login.jwt.JwtProperties;
import farmeasy.server.dto.TokenDto;
import farmeasy.server.dto.user.*;
import farmeasy.server.entity.user.Farm;
import farmeasy.server.entity.user.Role;
import farmeasy.server.entity.user.User;
import farmeasy.server.repository.FarmJpaRepo;
import farmeasy.server.repository.UserJpaRepo;
import farmeasy.server.util.exception.ResourceNotFoundException;
import farmeasy.server.util.exception.user.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserJpaRepo userJpaRepo;
    private final FarmJpaRepo farmJpaRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public UserDto join(JoinUserReq form) {
        isExistUsername(form.getUsername());
        checkPassword(form.getPassword(), form.getCheckPassword());

        String encodePwd = passwordEncoder.encode(form.getPassword());
        form.setPassword(encodePwd);

        User user = JoinUserReq.toEntity(form);
        userJpaRepo.save(user);

        return new UserDto(user.getId(), user.getName(), user.getAddress(),user.getBirthday());
    }

    @Override
    public UserTokenDto signIn(LoginReq req) {
        User user = authenticate(req.getUsername(), req.getPassword());
        checkEncodePassword(req.getPassword(),user.getPassword());
        TokenDto token = jwtProperties.generateToken(user);
        user.setRefreshToken(token.getRefreshToken());
        userJpaRepo.save(user);
        return UserTokenDto.toDto(user,token);
    }

    @Override
    @Transactional
    public RegisterFarmReq createFarm(RegisterFarmReq req, User user) {
        Farm farm = RegisterFarmReq.toEntity(req);
        farm.setUser(user);
        farmJpaRepo.save(farm);
        userJpaRepo.save(user);
        return req;
    }

    @Override
    public User getByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserException("인증 된 정보가 없습니다.",HttpStatus.BAD_REQUEST);
        }
        String username = authentication.getName();
        return findByUsername(username);
    }

    @Override
    public TokenDto refreshToken(String refreshToken){
        String username = jwtProperties.getUsernameFromToken(refreshToken);
        User user = findByUsername(username);
        validateRefreshToken(refreshToken, user.getRefreshToken());
        jwtProperties.validateToken(refreshToken,user);
        return jwtProperties.generateToken(user);
    }

    private User authenticate(String username, String pwd) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pwd));
            return (User) authentication.getPrincipal();
        } catch (DisabledException e) {
            throw new UserException("인증되지 않은 아이디입니다.", HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            throw new UserException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * 아이디(이메일) 중복 체크
     */
    private void isExistUsername(String username) {
        if (userJpaRepo.findByUsername(username).isPresent()) {
            throw new UserException("이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * 비밀번호와 비밀번호 확인이 같은지 체크
     */
    private void checkPassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new UserException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }



    /**
     * 사용자가 입력한 비번과 DB에 저장된 비번이 같은지 체크 : 인코딩 확인
     */
    private void checkEncodePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new UserException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateRefreshToken(String refreshToken, String userRefreshToken) {
        if (!refreshToken.equals(" "+userRefreshToken)) {
            throw new SecurityException("Refresh token이 일치하지 않습니다.");
        }
    }

    private User findByUsername(String username){
        return userJpaRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User","username",username));
    }

    private boolean isAuthorized(User user, Long authorId){
        return user.getRole().equals(Role.ADMIN) || user.getId().equals(authorId);
    }

    @Override
    public void checkUser(User user, Long authorId){
        if(!isAuthorized(user,authorId)) throw new UserException("해당 권한이 없습니다.", HttpStatus.BAD_REQUEST);
    }
}

