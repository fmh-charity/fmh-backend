package ru.iteco.fmh;


import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.model.user.User;
import java.util.Set;

import static ru.iteco.fmh.TestUtils.getRole;

@Service
@Primary
public class TestUserDetailsServiceImpl implements UserDetailsService {
    UserDetails admin = User.builder().id(1).login("admin").password("password")
            .userRoles(Set.of(getRole("ROLE_ADMINISTRATOR"))).build();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return admin;
    }
}
