package ru.iteco.fmh.converter.user;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.user.User;

/**
 * конвертер из {@link User} в {@link UserDtoIdFio}//для «Пациенты» (Для wishDto)
 */
@Component
public class UserToUserDtoIdFioConverter implements Converter<User, UserDtoIdFio> {

    @Override
    public UserDtoIdFio convert(User user) {
        Profile userProfile = user.getProfile();
        return new UserDtoIdFio(
                user.getId(),
                userProfile.getFirstName(),
                userProfile.getMiddleName(),
                userProfile.getLastName()
        );
    }
}
