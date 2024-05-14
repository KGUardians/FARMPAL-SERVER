package com.example.farmeasyserver.dto.user;

import com.example.farmeasyserver.entity.user.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinUserForm {
    private String username;
    private String password;
    private String checkPassword;
    private String name;
    private int year;
    private int month;
    private int day;
    private Gender gender;
    private String email;
    private Long zipcode;
    private String address;
    private String sido;
    private String sigungu;
    private String phoneNumber;

    public static User toEntity(JoinUserForm form){
        return new User(
                form.getUsername(),
                form.getPassword(),
                form.getName(),
                form.getGender(),
                form.getPhoneNumber(),
                form.getEmail(),
                new Day(form.getYear(), form.getMonth(), form.getDay()),
                new Address(form.getZipcode(), form.getAddress(), form.getSido(), form.getSigungu()),
                Role.USER
        );
    }
}
