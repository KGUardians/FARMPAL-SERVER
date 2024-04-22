package com.example.farmeasyserver.dto.user;

import com.example.farmeasyserver.entity.user.Address;
import com.example.farmeasyserver.entity.user.Day;
import com.example.farmeasyserver.entity.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinUserForm {
    private String username;
    private String password;
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
}
