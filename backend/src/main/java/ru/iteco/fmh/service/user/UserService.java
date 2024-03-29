package ru.iteco.fmh.service.user;

import org.springframework.data.domain.Pageable;
import ru.iteco.fmh.dto.role.RoleDto;
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
     @param pageable настройки вывода на экране
     Следующие параметры являются необязательными. Выборка будет осуществляться с учётом каждого из трёх при наличии:
     @param text фильтрация по одному из: firstName, lastName, middleName, email
     @param roleIds фильтрация по одному из идентификатору ролей
     @param isConfirmed фильтрация по статусу подтверждения пользователя
     @return краткая информация о пользователе
     */
    List<UserShortInfoDto> getAllUsers(Pageable pageable, String text, List<Integer> roleIds, Boolean isConfirmed);

    /**
     * Возвращает активного пользователя, если он есть
     */
    User getActiveUserByLogin(String login);


    /**
     * Администратор подтверждает роль пользователя
     */
    UserShortInfoDto confirmUserRole(int userId);

    /**
     * Возвращает информацию по пользователю, если он есть
     */
    UserInfoDto getUserInfo(Integer id);

    /**
     * Изменяет информацию по пользователю, если он есть
     */
    UserShortInfoDto updateUser(int userId, ProfileChangingRequest profileChangingRequest);

    /**
     * создает новую карточку сотрудника
     *
     * @param request информация по сотруднику для создания
     * @return сущность
     */
    EmployeeRegistrationResponse createEmployee(EmployeeRegistrationRequest request);

    /**
     * возвращает список всех ролей
     */
    List<RoleDto> getAllRoles();
}
