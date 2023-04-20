package ru.iteco.fmh.converter.user;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.user.ProfileDtoRs;
import ru.iteco.fmh.model.user.Profile;

@Component
@RequiredArgsConstructor
public class ProfileToProfileDtoRsConverter implements Converter<Profile, ProfileDtoRs> {
    @Override
    public ProfileDtoRs convert(Profile source) {
        return ProfileDtoRs.builder()
                .lastName(source.getLastName())
                .firstName(source.getFirstName())
                .middleName(source.getMiddleName())
                .email(source.getEmail())
                .dateOfBirth(source.getDateOfBirth())
                .emailConfirmed(source.isEmailConfirmed())
                .build();
    }
}
