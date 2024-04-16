package com.example.farmeasyserver.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import com.example.farmeasyserver.entity.board.Post;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String username;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phoneNumber;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Post> Posts = new ArrayList<>();

}
