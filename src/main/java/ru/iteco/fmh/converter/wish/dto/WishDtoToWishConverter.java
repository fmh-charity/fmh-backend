package ru.iteco.fmh.converter.wish.dto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.patient.dto.PatientDtoToPatientConverter;
import ru.iteco.fmh.converter.user.dto.UserDtoToUserConverter;
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
    public Wish convert(@NonNull WishDto dto) {
        Wish entity = new Wish();
        BeanUtils.copyProperties(dto, entity);

        Patient patient = dto.getPatient() != null ? dtoToPatientConverter.convert(dto.getPatient()) : null;
        User creator = dto.getCreator() != null ? userDtoToUserConverter.convert(dto.getCreator()) : null;
        User executor = dto.getExecutor() != null ? userDtoToUserConverter.convert(dto.getExecutor()) : null;

        entity.setPatient(patient);
        entity.setCreator(creator);
        entity.setExecutor(executor);

        return entity;
    }
}
