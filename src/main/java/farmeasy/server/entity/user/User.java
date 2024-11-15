package farmeasy.server.entity.user;

import farmeasy.server.entity.board.Post;
import farmeasy.server.entity.board.community.Comment;
import farmeasy.server.entity.board.exprience.ExpApplication;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String phoneNumber;
    private String email;
    private String birthday;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Embedded
    private Address address;
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Farm farm;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "applicants", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExpApplication> expApplications = new ArrayList<>();
    public User(String username,
                String password,
                String name,
                Gender gender,
                String phoneNumber,
                String email,
                String birthday,
                Address address,
                Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add( new SimpleGrantedAuthority("ROLE_" + this.role));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
