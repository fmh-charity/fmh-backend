package ru.iteco.fmh.model.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.iteco.fmh.model.Token;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Пользователь
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String login;
    String password;
    String firstName;
    String lastName;
    String middleName;
    String phoneNumber;
    String email;
    boolean deleted;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> userRoles;
    //к одному юзеру несколько токенов
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    List<Token> tokens;


    public User(Integer id, String login, String password, List<Token> tokens, List<Role> userRoles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.tokens = tokens;
        this.userRoles = userRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getName().getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", login='" + login + '\''
                + ", password='" + password + '\''
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", middleName='" + middleName + '\''
                + ", phoneNumber='" + phoneNumber + '\''
                + ", email='" + email + '\''
                + ", deleted=" + deleted
                + ", userRoles=" + userRoles
                + '}';
    }
}
