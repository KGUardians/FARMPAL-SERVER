package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.dto.user.JoinUserForm;
import com.example.farmeasyserver.dto.user.UserDto;
import com.example.farmeasyserver.entity.user.Address;
import com.example.farmeasyserver.entity.user.Day;
import com.example.farmeasyserver.entity.user.Role;
import com.example.farmeasyserver.repository.UserRepository;
import com.example.farmeasyserver.repository.jpa.UserJpaRepository;
import com.example.farmeasyserver.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto join(JoinUserForm form) {
        Address address = new Address(form.getZipcode(), form.getAddress(), form.getSido(), form.getSigungu());
        Day birthday = new Day(form.getYear(), form.getMonth(), form.getDay());

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
        userJpaRepository.save(user);
        return new UserDto(user.getId(), user.getName(), user.getAddress(),user.getBirthday());
    }

    @Override
    public User findUser(String userName) {
        return userJpaRepository.findByUsername(userName);
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userJpaRepository.findById(id);
    }
}
