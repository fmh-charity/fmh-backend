package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.user.ProfileToProfileDtoRsConverter;
import ru.iteco.fmh.converter.user.UserToUserDtoConverter;
import ru.iteco.fmh.dto.user.ProfileDtoRs;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.user.User;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getProfile;
import static ru.iteco.fmh.TestUtils.getUser;

public class UserToUserDtoConverterTest {
    ProfileToProfileDtoRsConverter profileToProfileDtoRsConverter = new ProfileToProfileDtoRsConverter();
    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter(profileToProfileDtoRsConverter);
    @Test
    void convert() {
        User user = getUser(getProfile());
        Profile userProfile = user.getProfile();
        UserDto userDto = userToUserDtoConverter.convert(user);
        ProfileDtoRs userDtoProfileDtoRs = userDto.getProfile();
        assertAll(
                () -> assertEquals(user.getId(), userDto.getId()),
                () -> assertEquals(userProfile.getFirstName(), userDtoProfileDtoRs.getFirstName()),
                () -> assertEquals(userProfile.getLastName(), userDtoProfileDtoRs.getLastName()),
                () -> assertEquals(userProfile.getMiddleName(), userDtoProfileDtoRs.getMiddleName()),
                () -> assertEquals(user.getLogin(), userDto.getLogin()),
                () -> assertEquals(userProfile.getEmail(), userDtoProfileDtoRs.getEmail())
        );
    }
}
