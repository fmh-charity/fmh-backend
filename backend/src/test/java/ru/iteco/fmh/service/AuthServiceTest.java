package ru.iteco.fmh.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.registration.RegistrationRequest;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static ru.iteco.fmh.TestUtils.getRegistrationRequest;
import static ru.iteco.fmh.TestUtils.getRole;
import static ru.iteco.fmh.TestUtils.getUser;

@ExtendWith({MockitoExtension.class})
public class AuthServiceTest {
    @InjectMocks
    @Spy
    AuthService authService;
    @Mock
    ConversionService conversionService;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    PasswordEncoder encoder;

    @Test
    void userRegistration() {
        RegistrationRequest registrationRequest = getRegistrationRequest();
        User convertedRegistrationRequest = getUser();
        User user = getUser();
        Role role = getRole("ROLE_ANY");
        List<Role> roleList = List.of(role);

        doReturn(null).when(userRepository).findUserByLogin(registrationRequest.getEmail());
        doReturn(convertedRegistrationRequest).when(conversionService).convert(registrationRequest, User.class);
        doReturn(roleList).when(roleRepository).findAllByIdIn(registrationRequest.getRoleIds());
        doReturn(roleList).when(authService).getRolesListWithoutNeedConfirm(roleList);
        doReturn("123").when(encoder).encode(registrationRequest.getPassword());
        doReturn(user).when(userRepository).save(convertedRegistrationRequest);
        doNothing().when(authService).createRolesClaim(roleList, user);

        authService.userRegistration(registrationRequest);

        verify(authService).userRegistration(registrationRequest);
    }
}
