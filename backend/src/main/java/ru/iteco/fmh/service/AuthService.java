package ru.iteco.fmh.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dao.repository.TokenRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.user.ResetPasswordRequest;
import ru.iteco.fmh.dto.role.RoleDtoRs;
import ru.iteco.fmh.dto.user.UserRoleClaimShort;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.exceptions.InvalidLoginException;
import ru.iteco.fmh.exceptions.InvalidTokenException;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.exceptions.UnavailableOperationException;
import ru.iteco.fmh.exceptions.UserExistsException;
import ru.iteco.fmh.model.Token;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.RoleClaimStatus;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.security.JwtProvider;
import ru.iteco.fmh.security.JwtResponse;
import ru.iteco.fmh.security.LoginRequest;
import ru.iteco.fmh.security.RefreshTokenRequest;
import ru.iteco.fmh.service.user.UserService;
import ru.iteco.fmh.dto.registration.RegistrationRequest;
import ru.iteco.fmh.service.user.UserRoleClaimServiceImpl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final ConversionService conversionService;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final UserRoleClaimServiceImpl userRoleClaimService;
    private final UserService userService;

    @Transactional
    public JwtResponse authenticateUser(LoginRequest loginRequest) {

        if (loginRequest.getLogin() == null || loginRequest.getPassword() == null) {
            LOGGER.error("Логин и пароль не могут ровняться null");
            throw new InvalidLoginException();
        }
        User user = userRepository.findUserByLogin(loginRequest.getLogin());

        if (user == null) {
            LOGGER.error("пользователь не найден");
            throw new InvalidLoginException();
        }

        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            LOGGER.error("Неверный пароль");
            throw new InvalidLoginException();
        }
        //get token
        String accessJwtToken = jwtProvider.generateAccessJwtToken(user);
        String refreshJwtToken = jwtProvider.generateRefreshJwtToken(user);

        Token newToken = Token.builder()
                .user(user)
                .refreshToken(refreshJwtToken)
                .createDate(Instant.now())
                .disabled(false)
                .deleted(false)
                .build();

        tokenRepository.save(newToken);

        return new JwtResponse(accessJwtToken, refreshJwtToken);

    }


    public JwtResponse refreshToken(RefreshTokenRequest refreshToken) {
        Token token = tokenRepository.findTokenByRefreshToken(refreshToken.getRefreshToken());
        if (token == null || token.isDisabled()) {
            throw new InvalidTokenException();
        }
        token.setDisabled(true);
        tokenRepository.save(token);


        User user = userRepository.findUserById(token.getUser().getId());
        String accessJwtToken = jwtProvider.generateAccessJwtToken(user);
        String refreshJwtToken = jwtProvider.generateRefreshJwtToken(user);

        Token newToken = Token.builder()
                .user(user)
                .refreshToken(refreshJwtToken)
                .createDate(Instant.now())
                .disabled(false)
                .deleted(false)
                .build();
        tokenRepository.save(newToken);
        return new JwtResponse(accessJwtToken, refreshJwtToken);
    }

    public UserShortInfoDto getAuthorizedUser(Authentication authentication) {
        return conversionService.convert(userRepository.findUserByLogin(authentication.getName()),
                UserShortInfoDto.class);
    }

    public void userRegistration(RegistrationRequest registrationRequest) {
        User foundedUser = userRepository.findUserByLogin(registrationRequest.getEmail());
        if (foundedUser != null) {
            throw new UserExistsException("Пользователь с данным email уже существует");
        }
        User user = conversionService.convert(registrationRequest, User.class);
        List<Role> desiredRoles = roleRepository.findAllByIdIn(registrationRequest.getRoleIds());
        List<Role> allowedRoles = getRolesListWithoutNeedConfirm(desiredRoles);
        user.setPassword(encoder.encode(registrationRequest.getPassword()));
        user.setUserRoles(allowedRoles);
        User dbUser = userRepository.save(user);
        createRolesClaim(desiredRoles, dbUser);
        LOGGER.info(String.format("Пользователь с логином %s успешно зарегистрирован", dbUser.getEmail()));
    }

    public List<Role> getRolesListWithoutNeedConfirm(List<Role> roles) {
        Optional<Role> guestRole = roleRepository.findRoleByName("ROLE_GUEST");
        List<Role> allowedRoles = roles.stream().filter(role -> !role.isNeedConfirm()).collect(Collectors.toList());
        allowedRoles.add(guestRole.get());
        return allowedRoles;
    }

    public void createRolesClaim(List<Role> roles, User user) {
        List<Role> rolesListWithNeedConfirm = roles.stream().filter(Role::isNeedConfirm).toList();
        rolesListWithNeedConfirm.forEach(role -> userRoleClaimService.create(
                UserRoleClaimShort.builder()
                        .roleId(role.getId())
                        .userId(user.getId())
                        .status(RoleClaimStatus.NEW)
                        .build()));

    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String login = resetPasswordRequest.getLogin();
        User user = userService.getActiveUserByLogin(login);
        if (!user.isEmailConfirmed()) {
            throw new UnavailableOperationException("Email не подтверждён!");
        }
        String password = encoder.encode(resetPasswordRequest.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    public List<RoleDtoRs> getAvailableRoles() {
        return roleRepository.findAllByDeletedIsFalse().stream()
                .map(role -> conversionService.convert(role, RoleDtoRs.class)).toList();
    }
}
