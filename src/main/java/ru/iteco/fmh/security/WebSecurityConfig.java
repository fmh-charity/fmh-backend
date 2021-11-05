package ru.iteco.fmh.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import ru.iteco.fmh.dao.repository.UserRepository;

@Configuration
@EnableWebSecurity //позволяет Spring находить и автоматически применять класс к глобальной веб-безопасности.
@EnableConfigurationProperties// Аннотация @EnableConfigurationProperties указывает, что класс будет содержать
// в качестве особого конфигурационного бина. Not sure that it needs, but this was in example
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    UserDetailsServiceImpl userDetailsServiceImpl;
    UserRepository userRepository;
    private final AuthEntryPointJwt unauthorizedHandler;


    public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, UserRepository userRepository,
                             AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.userRepository = userRepository;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    //https://www.baeldung.com/spring-security-login#1-authentication-manager
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder());
    }

    //https://www.baeldung.com/spring-security-login#3-configuration-for-form-login
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/authentication/login", "/authentication/check").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
