package ru.iteco.fmh.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.model.user.User;

@Service
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = userRepository.findUserByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with -> Login : " + login);
        }

        return UserPrinciple.build(user);
    }
}
