package ru.iteco.fmh.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.iteco.fmh.model.user.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

//Он просто хранит информацию о пользователе, которая позже инкапсулируется в объекты аутентификации.
// Это позволяет хранить информацию о пользователях, не связанную с безопасностью
public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final int id;

    private final String login;


    @JsonIgnore
    private final String password;

    public UserPrinciple(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public static UserPrinciple build(User user, List<SimpleGrantedAuthority> authorities) {
        return new UserPrinciple(
                user.getId(),
                user.getLogin(),
                user.getPassword()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }

}
