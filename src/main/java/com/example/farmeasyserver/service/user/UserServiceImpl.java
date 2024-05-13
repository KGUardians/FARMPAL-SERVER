package com.example.farmeasyserver.service.user;

import com.example.farmeasyserver.dto.user.JoinUserForm;
import com.example.farmeasyserver.dto.user.UserDto;
import com.example.farmeasyserver.entity.user.Address;
import com.example.farmeasyserver.entity.user.Day;
import com.example.farmeasyserver.entity.user.Role;
import com.example.farmeasyserver.repository.UserJpaRepo;
import com.example.farmeasyserver.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepo userRepository;

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
        userRepository.save(user);
        return new UserDto(user.getId(), user.getName(), user.getAddress(),user.getBirthday());
    }

}
