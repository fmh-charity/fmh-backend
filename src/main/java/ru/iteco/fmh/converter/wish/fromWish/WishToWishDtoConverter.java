package ru.iteco.fmh.converter.wish.fromWish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.patient.fromPatient.PatientToPatientDtoConverter;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;

@Component
@RequiredArgsConstructor
public class WishToWishDtoConverter implements Converter<Wish, WishDto> {

    private final PatientToPatientDtoConverter patientToDtoConverter;
    private final UserToUserDtoConverter userToUserDtoConverter;

    @Override
    public WishDto convert(@NonNull Wish wish) {
        WishDto dto = new WishDto();
        BeanUtils.copyProperties(wish, dto);

        PatientDto patientDto = wish.getPatient()!=null? patientToDtoConverter.convert(wish.getPatient()) : null;
        UserDto creator = wish.getCreator()!=null? userToUserDtoConverter.convert(wish.getCreator()) : null;
        UserDto executor = wish.getExecutor()!=null? userToUserDtoConverter.convert(wish.getExecutor()) :null;

        dto.setPatient(patientDto);
        dto.setExecutor(executor);
        dto.setCreator(creator);
        return dto;
    }
}
