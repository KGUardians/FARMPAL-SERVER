package farmeasy.server.user.service;

import farmeasy.server.config.cookie.CookieManager;
import farmeasy.server.config.login.jwt.JwtManager;
import farmeasy.server.config.login.jwt.dto.TokenDto;
import farmeasy.server.user.domain.Role;
import farmeasy.server.user.domain.User;
import farmeasy.server.user.repository.UserJpaRepo;
import farmeasy.server.user.dto.JoinUserReq;
import farmeasy.server.user.dto.LoginReq;
import farmeasy.server.user.dto.UserDto;
import farmeasy.server.util.exception.user.UserException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJpaRepo userJpaRepo;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;
    private final CookieManager cookieManager;

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
    @Transactional
    public ResponseEntity<String> signIn(LoginReq req, HttpServletResponse response) {
        User user = authenticate(req.getUsername(), req.getPassword());
        checkEncodePassword(req.getPassword(),user.getPassword());

        TokenDto token = jwtManager.generateToken(user);
        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();

        user.setRefreshToken(token.getRefreshToken());
        cookieManager.putRefreshTokenInCookie(response, refreshToken);
        return ResponseEntity.ok(accessToken);
    }

    @Override
    @Transactional
    public ResponseEntity<String> refreshToken(Cookie[] cookies, User user){
        return jwtManager.refreshToken(cookies, user);
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

    private boolean isAuthorized(User user, Long authorId){
        return user.getRole().equals(Role.ADMIN) || user.getId().equals(authorId);
    }

    @Override
    public void checkUser(User user, Long authorId){
        if(!isAuthorized(user,authorId)) throw new UserException("해당 권한이 없습니다.", HttpStatus.BAD_REQUEST);
    }


}

