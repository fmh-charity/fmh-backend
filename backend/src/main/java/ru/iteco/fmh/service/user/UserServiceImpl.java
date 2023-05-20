package ru.iteco.fmh.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.EmployeeRepository;
import ru.iteco.fmh.dao.repository.PositionRepository;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.UserRoleClaimRepository;
import ru.iteco.fmh.dto.employee.EmployeeRegistrationRequest;
import ru.iteco.fmh.dto.employee.EmployeeRegistrationResponse;
import ru.iteco.fmh.dto.user.UserInfoDto;
import ru.iteco.fmh.dto.user.UserRoleClaimDto;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.exceptions.IncorrectDataException;
import ru.iteco.fmh.exceptions.InvalidLoginException;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.employee.Employee;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.user.RoleClaimStatus;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.model.user.UserRoleClaim;
import ru.iteco.fmh.service.mail.notifier.Notifier;
import ru.iteco.fmh.service.mail.notifier.SendEmailNotifierContext;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static ru.iteco.fmh.Util.MEDICAL_WORKER_ROLE;
import static ru.iteco.fmh.model.user.RoleClaimStatus.CONFIRMED;
import static ru.iteco.fmh.model.user.RoleClaimStatus.NEW;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleClaimRepository userRoleClaimRepository;
    private final RoleRepository roleRepository;
    private final ConversionService conversionService;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    @Value("${spring.mail.username}")
    private String emailFromAddress;
    private final Notifier<SendEmailNotifierContext> sendEmailNotifier;

    private final PasswordEncoder encoder;

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
    @Transactional
    public EmployeeRegistrationResponse createEmployee(EmployeeRegistrationRequest request) {
        String password = generateRandomPassword();
        var user = User.builder().login(request.getEmail())
                .password(encoder.encode(password))
                .userRoles(new HashSet<>()).build();
        var role = roleRepository.findRoleByName(MEDICAL_WORKER_ROLE)
                .orElseThrow(() -> new IllegalArgumentException("Не найдена роль мед сотрудника"));
        user.getUserRoles().add(role);
        var profile = Profile.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .middleName(request.getMiddleName()).email(request.getEmail()).dateOfBirth(request.getDateOfBirth())
                .build();
        user.setProfile(profile);
        var position = positionRepository.findById(request.getPositionId())
                .orElseThrow(() -> new NotFoundException("Должности с таким id не существует"));
        var employee = Employee.builder()
                .profile(profile)
                .position(position)
                .description(request.getDescription())
                .scheduleType(request.getScheduleType())
                .active(true)
                .workStartTime(request.getWorkStartTime())
                .workEndTime(request.getWorkEndTime())
                .scheduleStartDate(request.getScheduleStartDate()).build();
        userRepository.save(user);
        employeeRepository.save(employee);
        String content = "Вы успешно зарегестрированы в приложени Вхосписе. Ваш логин : "
                + user.getLogin() + " Ваш пароль : " + password;

        SendEmailNotifierContext sendEmailNotifierContext = SendEmailNotifierContext.builder()
                .toAddress(user.getProfile().getEmail())
                .fromAddress(emailFromAddress)
                .senderName("Vhospice")
                .subject("Регистрация vhospice.org")
                .content(content)
                .build();
        sendEmailNotifier.send(sendEmailNotifierContext);
        return EmployeeRegistrationResponse.builder()
                .employeeId(employee.getId())
                .lastName(profile.getLastName())
                .firstName(profile.getFirstName())
                .middleName(profile.getMiddleName())
                .email(user.getLogin())
                .dateOfBirth(profile.getDateOfBirth())
                .position(employee.getPosition().getName())
                .description(employee.getDescription())
                .isActive(employee.getActive())
                .scheduleType(employee.getScheduleType())
                .scheduleStartDate(employee.getScheduleStartDate())
                .workStartTime(employee.getWorkStartTime())
                .workEndTime(employee.getWorkEndTime()).build();
    }

    public static String generateRandomPassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
}
