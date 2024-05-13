package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.config.login.jwt.JwtProperties;
import com.example.farmeasyserver.dto.user.JoinUserForm;
import com.example.farmeasyserver.dto.user.LoginRequest;
import com.example.farmeasyserver.dto.user.UserDto;
import com.example.farmeasyserver.dto.user.UserTokenDto;
import com.example.farmeasyserver.entity.user.Address;
import com.example.farmeasyserver.entity.user.Day;
import com.example.farmeasyserver.entity.user.Role;
import com.example.farmeasyserver.repository.UserJpaRepo;
import com.example.farmeasyserver.entity.user.User;
import com.example.farmeasyserver.util.exception.ResourceNotFoundException;
import com.example.farmeasyserver.util.exception.user.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserJpaRepo userJpaRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public UserDto join(JoinUserForm form) {
        Address address = new Address(form.getZipcode(), form.getAddress(), form.getSido(), form.getSigungu());
        Day birthday = new Day(form.getYear(), form.getMonth(), form.getDay());

        String encodePwd = passwordEncoder.encode(form.getPassword());
        form.setPassword(encodePwd);

        User user = new User(
                form.getUsername(),
                form.getPassword(),
                form.getName(),
                form.getGender(),
                form.getPhoneNumber(),
                form.getEmail(),
                birthday,
                address,
                Role.USER
        );
        userJpaRepo.save(user);
        return new UserDto(user.getId(), user.getName(), user.getAddress(),user.getBirthday());
    }

    @Override
    public UserTokenDto signIn(LoginRequest req) {
        authenticate(req.getUsername(), req.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        checkEncodePassword(req.getPassword(),userDetails.getPassword());

        User user = userJpaRepo.findByUsername(req.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("User","User Email",req.getUsername())
        );

        String token = jwtProperties.generateToken(userDetails);
        return UserTokenDto.fromEntity(user,token);
    }

    private void authenticate(String username, String pwd) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pwd));
        } catch (DisabledException e) {
            throw new UserException("인증되지 않은 아이디입니다.", HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            throw new UserException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * 아이디(이메일) 중복 체크
     * @param username
     */
    private void isExistUsername(String username) {
        if (userJpaRepo.findByUsername(username).isPresent()) {
            throw new UserException("이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * 비밀번호와 비밀번호 확인이 같은지 체크
     * @param password
     * @param passwordCheck
     */
    private void checkPassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new UserException("패스워드 불일치", HttpStatus.BAD_REQUEST);
        }
    }



    /**
     * 사용자가 입력한 비번과 DB에 저장된 비번이 같은지 체크 : 인코딩 확인
     * @param rawPassword
     * @param encodedPassword
     */
    private void checkEncodePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new UserException("패스워드 불일치", HttpStatus.BAD_REQUEST);
        }
    }

}
