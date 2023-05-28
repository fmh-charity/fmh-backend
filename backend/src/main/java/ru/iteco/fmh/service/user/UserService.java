package ru.iteco.fmh.service.user;

import ru.iteco.fmh.dto.user.ProfileChangingRequest;
import ru.iteco.fmh.dto.employee.EmployeeRegistrationRequest;
import ru.iteco.fmh.dto.employee.EmployeeRegistrationResponse;
import ru.iteco.fmh.dto.user.UserInfoDto;
import org.springframework.data.domain.PageRequest;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.model.user.User;

import java.util.List;

/**
 * сервис для работы с users
 */
public interface UserService {

    /**
     * возвращает список всех users
     */
    List<UserShortInfoDto> getAllUsers(PageRequest pageRequest, Boolean showConfirmed);

    /**
     * Возвращает активного пользователя, если он есть
     */
    User getActiveUserByLogin(String login);


    /**
     * Администратор подтверждает роль пользователя
     */
    UserShortInfoDto confirmUserRole(int userId);

    /**
     *
     *  Возвращает информацию по пользователю, если он есть
     */
    UserInfoDto getUserInfo(Integer id);

    /**
     *
     *  Изменяет информацию по пользователю, если он есть
     */
    UserShortInfoDto updateUser(int userId, ProfileChangingRequest profileChangingRequest);

    /**
     * создает новую карточку сотрудника
     * @param request информация по сотруднику для создания
     * @return сущность
     */
    EmployeeRegistrationResponse createEmployee(EmployeeRegistrationRequest request);
}
