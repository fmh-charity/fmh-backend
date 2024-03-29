package ru.iteco.fmh.converter.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.user.User;

import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.dto.registration.RegistrationRequest;

import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor
public class RegistrationRequestToUserConverter implements Converter<RegistrationRequest, User> {
    @Override
    public User convert(@NotNull RegistrationRequest source) {
        Profile userProfile = Profile.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .middleName(source.getMiddleName())
                .email(source.getEmail())
                .dateOfBirth(source.getDateOfBirth())
                .deleted(false)
                .emailConfirmed(false)
                .build();
        return User.builder()
                .login(source.getEmail())
                .profile(userProfile)
                .deleted(false)
                .build();
    }
}
