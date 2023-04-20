package ru.iteco.fmh.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.Util;
import ru.iteco.fmh.converter.user.UserToUserShortInfoDtoConverter;
import ru.iteco.fmh.dao.repository.ProfileRepository;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dao.repository.TokenRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.registration.RegistrationRequest;
import ru.iteco.fmh.dto.role.RoleDtoRs;
import ru.iteco.fmh.dto.user.ResetPasswordRequest;
import ru.iteco.fmh.dto.user.UserRoleClaimShort;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.exceptions.InvalidLoginException;
import ru.iteco.fmh.exceptions.InvalidTokenException;
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
import ru.iteco.fmh.service.user.UserRoleClaimService;
import ru.iteco.fmh.service.user.UserService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;
    private final ConversionService conversionService;
    private final UserToUserShortInfoDtoConverter userToUserShortInfoDtoConverter;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;
    private final UserRoleClaimService userRoleClaimService;
    private final UserService userService;
    private static final List<String> availableToApplyRoles = List.of("ROLE_VOLUNTEER");

    @Transactional
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        String notNullMessage = "Логин и пароль не могут ровняться null";
        String userNotFoundMessage = "Пользователь с логином %s не найден";
        String wrongPasswordMessage = "Введен неверный пароль";

        if (loginRequest.getLogin() == null || loginRequest.getPassword() == null) {
            LOGGER.error(notNullMessage);
            throw new InvalidLoginException(notNullMessage);
        }
        User user = userRepository.findUserByLogin(loginRequest.getLogin());

        if (user == null) {
            LOGGER.error(String.format(userNotFoundMessage, loginRequest.getLogin()));
            throw new InvalidLoginException(String.format(userNotFoundMessage, loginRequest.getLogin()));
        }

        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            LOGGER.error(wrongPasswordMessage);
            throw new InvalidLoginException(wrongPasswordMessage);
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

    @Transactional
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

    public UserShortInfoDto getAuthorizedUser() {
        return userToUserShortInfoDtoConverter.convert(Util.getCurrentLoggedInUser());
    }

    @Transactional
    public void userRegistration(RegistrationRequest registrationRequest) {
        User foundedUser = userRepository.findUserByLogin(registrationRequest.getEmail());
        if (foundedUser != null) {
            throw new UserExistsException("Пользователь с данным email уже существует");
        }
        User user = conversionService.convert(registrationRequest, User.class);
        profileRepository.findByEmail(registrationRequest.getEmail()).ifPresent(profile -> user.setProfile(profile));

        List<Role> desiredRoles = roleRepository.findAllByIdIn(registrationRequest.getRoleIds());
        Set<Role> allowedRoles = getRolesListWithoutNeedConfirm(desiredRoles);

        user.setPassword(encoder.encode(registrationRequest.getPassword()));
        user.setUserRoles(allowedRoles);
        User dbUser = userRepository.save(user);
        createRolesClaim(desiredRoles, dbUser);
        LOGGER.info(String.format("Пользователь с логином %s успешно зарегистрирован", dbUser.getProfile().getEmail()));
    }

    public Set<Role> getRolesListWithoutNeedConfirm(List<Role> roles) {
        Optional<Role> guestRole = roleRepository.findRoleByName("ROLE_GUEST");
        Set<Role> allowedRoles = roles.stream().filter(role -> !role.isNeedConfirm()).collect(Collectors.toSet());
        allowedRoles.add(guestRole.get());
        return allowedRoles;
    }

    public void createRolesClaim(List<Role> roles, User user) {
        Set<Role> rolesListWithNeedConfirm = roles.stream()
                .filter(role -> role.isNeedConfirm() && availableToApplyRoles.contains(role.getName())).collect(Collectors.toSet());
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
        if (!user.getProfile().isEmailConfirmed()) {
            throw new UnavailableOperationException("Email не подтверждён!");
        }
        String password = encoder.encode(resetPasswordRequest.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    public List<RoleDtoRs> getAvailableRoles() {
        return roleRepository.findAllByNeedConfirmIsTrueAndNameIn(availableToApplyRoles)
                .stream().map(role -> conversionService.convert(role, RoleDtoRs.class)).collect(Collectors.toList());
    }
}
