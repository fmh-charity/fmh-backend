package ru.iteco.fmh.service.user;

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
    public User getActiveUserByLogin(String login);


    /**
     * Администратор подтверждает роль пользователя
     */
    UserShortInfoDto confirmUserRole(int userId);
}
