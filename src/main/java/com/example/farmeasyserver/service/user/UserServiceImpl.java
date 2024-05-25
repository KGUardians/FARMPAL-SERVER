package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.config.login.auth.CustomUserDetailsService;
import com.example.farmeasyserver.config.login.jwt.JwtProperties;
import com.example.farmeasyserver.dto.user.*;
import com.example.farmeasyserver.entity.user.Farm;
import com.example.farmeasyserver.repository.FarmJpaRepo;
import com.example.farmeasyserver.repository.UserJpaRepo;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.util.exception.user.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
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
        String token = jwtProperties.generateToken(user);
        return UserTokenDto.toDto(user,token);
    }

    @Override
    @Transactional
    public RegisterFarmReq createFarm(RegisterFarmReq req,User user) {
        Farm farm = RegisterFarmReq.toEntity(req);
        farm.setUser(user);
        farmJpaRepo.save(farm);
        userJpaRepo.save(user);
        return req;
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
            throw new UserException("패스워드 불일치", HttpStatus.BAD_REQUEST);
        }
    }



    /**
     * 사용자가 입력한 비번과 DB에 저장된 비번이 같은지 체크 : 인코딩 확인
     */
    private void checkEncodePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new UserException("패스워드 불일치", HttpStatus.BAD_REQUEST);
        }
    }

}
