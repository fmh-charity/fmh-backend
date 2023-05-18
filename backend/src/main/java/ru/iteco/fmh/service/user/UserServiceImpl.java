package ru.iteco.fmh.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.UserRoleClaimRepository;
import ru.iteco.fmh.dao.repository.UserRoleClaimRepository;
import ru.iteco.fmh.dto.user.ProfileChangingRequest;
import ru.iteco.fmh.dto.user.UserInfoDto;
import ru.iteco.fmh.dto.user.UserRoleClaimDto;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.exceptions.IncorrectDataException;
import ru.iteco.fmh.exceptions.InvalidLoginException;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.user.RoleClaimStatus;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.model.user.UserRoleClaim;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.iteco.fmh.model.user.RoleClaimStatus.CONFIRMED;
import static ru.iteco.fmh.model.user.RoleClaimStatus.NEW;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleClaimRepository userRoleClaimRepository;
    private final RoleRepository roleRepository;

    private final ConversionService conversionService;

    @Override
    public List<UserShortInfoDto> getAllUsers(PageRequest pageRequest, Boolean showConfirmed) {
        if (showConfirmed == null) {
            return userRepository.findAll(pageRequest).getContent().stream()
                    .map(i -> conversionService.convert(i, UserShortInfoDto.class)).collect(Collectors.toList());
        } else if (!showConfirmed) {
            return userRepository.findAllByRoleClaimIsNewOrRejected(pageRequest).stream()
                    .map(i -> conversionService.convert(i, UserShortInfoDto.class)).collect(Collectors.toList());
        } else {
            return userRepository.findAllByRoleClaimIsConfirmedOrNull(pageRequest).stream()
                    .map(i -> conversionService.convert(i, UserShortInfoDto.class)).collect(Collectors.toList());
        }
    }

    @Override
    public User getActiveUserByLogin(String login) {
        User user = userRepository.findUserByLogin(login);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (user.isDeleted()) {
            throw new InvalidLoginException("Пользователь удален, обратитесь к администратору!");
        }
        return user;
    }

    @Override
    public UserShortInfoDto confirmUserRole(int userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким id не найден"));
        var userRoleClaim = userRoleClaimRepository.findByUserId(userId)
                .stream().filter(el -> el.getStatus() == NEW).findFirst()
                .orElseThrow(() -> new IncorrectDataException("У данного пользователя нет заявок на подтверждение роли"));
        user.getUserRoles().add(userRoleClaim.getRole());
        userRoleClaim.setStatus(CONFIRMED);
        userRepository.save(user);
        userRoleClaimRepository.save(userRoleClaim);
        return conversionService.convert(user, UserShortInfoDto.class);
    }

    @Override
    public UserInfoDto getUserInfo(Integer id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }

        UserInfoDto userInfoDto = conversionService.convert(user, UserInfoDto.class);

        UserRoleClaim userRoleClaim = userRoleClaimRepository.findFirstByUserIdAndStatusIn(id,
                List.of(RoleClaimStatus.NEW, RoleClaimStatus.REJECTED));
        if (userRoleClaim != null) {
            UserRoleClaimDto userRoleClaimDto = conversionService.convert(userRoleClaim, UserRoleClaimDto.class);
            userInfoDto.setUserRoleClaim(userRoleClaimDto);
            userInfoDto.setConfirmed(false);
        } else {
            userInfoDto.setConfirmed(true);
        }

        return userInfoDto;
    }

    @Override
    public UserShortInfoDto updateUser(int userId, ProfileChangingRequest profileChangingRequest) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }

        Profile profile = user.getProfile();
        profile.setFirstName(profileChangingRequest.getFirstName());
        profile.setLastName(profileChangingRequest.getLastName());
        profile.setMiddleName(profileChangingRequest.getMiddleName());
        profile.setDateOfBirth(profileChangingRequest.getDateOfBirth());
        profile.setEmail(profileChangingRequest.getEmail());
        user.setUserRoles(Set.copyOf(roleRepository.findAllByIdIn(List.copyOf(profileChangingRequest.getRoleIds()))));

        userRepository.save(user);

        return conversionService.convert(user, UserShortInfoDto.class);
    }
}
