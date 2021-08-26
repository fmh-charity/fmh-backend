package ru.iteco.fmh.converter.wish.fromWish;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.patient.fromPatient.IPatientToPatientDtoConverter;
import ru.iteco.fmh.converter.user.fromUser.IUserToUserDtoConverter;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.task.wish.Wish;

import java.util.Optional;


public class WishToWishDtoConverter implements Converter<Wish, WishDto> {
    private final IPatientToPatientDtoConverter patientToDtoConverter;
    private final IUserToUserDtoConverter userToUserDtoConverter;

    public WishToWishDtoConverter(IPatientToPatientDtoConverter patientToDtoConverter,
                                  IUserToUserDtoConverter userToUserDtoConverter) {
        this.patientToDtoConverter = patientToDtoConverter;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    @Override
    public WishDto convert(Wish wish) {
        WishDto dto = new WishDto();
        BeanUtils.copyProperties(wish, dto);

        PatientDto patientDto = patientToDtoConverter.convert(wish.getPatient());
        UserDto creator = userToUserDtoConverter.convert(wish.getCreator());
        UserDto executor = wish.getExecutor()!=null? userToUserDtoConverter.convert(wish.getExecutor()):null;

        dto.setPatient(patientDto);
        dto.setExecutor(executor);
        dto.setCreator(creator);
        return dto;
    }
}
