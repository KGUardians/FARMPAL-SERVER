package com.example.farmeasyserver.user.domain;

import farmeasy.server.user.domain.Address;
import farmeasy.server.user.domain.Gender;
import farmeasy.server.user.domain.Role;
import farmeasy.server.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserTest {


    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "password", "Test Name", Gender.MALE, "010-1234-5678",
                "test@example.com", "1990-01-01", new Address(12345L, "경기 수원시 장안구 창훈로52번길 22","경기도","수원시 장안구"), Role.USER);
    }

    @Test
    void 유저_권한_확인() {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertNotNull(authorities);
        assertFalse(authorities.isEmpty());
        assertEquals("ROLE_USER", authorities.iterator().next().getAuthority());
    }


    @Test
    void 유저_생성_정보_확인() {
        assertEquals("testUser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("Test Name", user.getName());
        assertEquals(Gender.MALE, user.getGender());
        assertEquals("010-1234-5678", user.getPhoneNumber());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("1990-01-01", user.getBirthday());
        assertEquals(Role.USER, user.getRole());
        assertEquals("수원시 장안구", user.getAddress().getSigungu());
    }
}
