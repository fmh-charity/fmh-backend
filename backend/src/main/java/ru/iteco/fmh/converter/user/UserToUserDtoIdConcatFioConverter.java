package ru.iteco.fmh.converter.user;

import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.user.UserDtoIdConcatFio;
import ru.iteco.fmh.model.user.User;
import org.springframework.core.convert.converter.Converter;

import java.util.Locale;

/**
 * конвертер из {@link User} в {@link UserDtoIdConcatFio}//для «Документы» (Для DocumentForAdminRs)
 */
@Component
public class UserToUserDtoIdConcatFioConverter implements Converter<User, UserDtoIdConcatFio> {
    @Override
    public UserDtoIdConcatFio convert(User user) {
        return new UserDtoIdConcatFio(
                user.getId(),
                String.format("%s %s. %s.", user.getLastName(), user.getFirstName().toUpperCase(Locale.ROOT).charAt(0),
                        user.getMiddleName().toUpperCase(Locale.ROOT).charAt(0))
        );
    }
}
