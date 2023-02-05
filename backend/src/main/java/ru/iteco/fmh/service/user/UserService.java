package ru.iteco.fmh.service.user;

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

    boolean isUserExist(User user);
}
