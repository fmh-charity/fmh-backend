package ru.iteco.fmh.service.user;

import ru.iteco.fmh.dto.user.UserDto;
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
    List<UserShortInfoDto> getAllUsers();

    /**
     *
     *  Возвращает активного пользователя, если он есть
     */
    User getActiveUserByLogin(String login);

}
