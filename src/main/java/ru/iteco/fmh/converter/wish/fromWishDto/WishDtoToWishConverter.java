package ru.iteco.fmh.converter.wish.fromWishDto;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.patient.fromPatientDto.PatientDtoToPatientConverter;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.user.User;

@Component
@RequiredArgsConstructor
public class WishDtoToWishConverter implements Converter<WishDto, Wish> {

    private final PatientDtoToPatientConverter dtoToPatientConverter;
    private final UserDtoToUserConverter userDtoToUserConverter;

    @Override
    public Wish convert(WishDto dto) {
        Wish entity = new Wish();
        BeanUtils.copyProperties(dto, entity);
        Patient patient = dtoToPatientConverter.convert(dto.getPatient());
        User creator = userDtoToUserConverter.convert(dto.getCreator());
        User executor = userDtoToUserConverter.convert(dto.getExecutor());

        entity.setPatient(patient);
        entity.setCreator(creator);
        entity.setExecutor(executor);

        return entity;
    }
}
