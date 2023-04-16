package ru.iteco.fmh.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.UserRoleClaimRepository;
import ru.iteco.fmh.dao.repository.UserRoleClaimRepository;
import ru.iteco.fmh.dto.user.UserInfoDto;
import ru.iteco.fmh.dto.user.UserRoleClaimDto;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.exceptions.IncorrectDataException;
import ru.iteco.fmh.exceptions.InvalidLoginException;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.user.RoleClaimStatus;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.model.user.UserRoleClaim;

import java.util.List;
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
    public List<UserShortInfoDto> getAllUsers() {
        List<User> list = userRepository.findAll();
        return list.stream()
                .map(i -> conversionService.convert(i, UserShortInfoDto.class))
                .collect(Collectors.toList());
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
        UserRoleClaimDto userRoleClaimDto;
        if (userRoleClaim != null) {
            userRoleClaimDto = conversionService.convert(userRoleClaim, UserRoleClaimDto.class);
            userRoleClaimDto.setRole(userRoleClaim.getRole().getName());
            userInfoDto.setUserRoleClaim(userRoleClaimDto);
            userInfoDto.setConfirmed(false);
        } else {
            userInfoDto.setConfirmed(true);
        }

        return userInfoDto;
    }
}
