package ru.iteco.fmh.converter.wish.fromWishDto;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.patient.fromPatientDto.IPatientDtoToPatientConverter;
import ru.iteco.fmh.converter.user.fromUserDto.IUserDtoToUserConverter;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.wish.Wish;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.User;

public class WishDtoToWishConverter implements Converter<WishDto, Wish> {

    private final IPatientDtoToPatientConverter dtoToPatientConverter;
    private final IUserDtoToUserConverter userDtoToUserConverter;

    public WishDtoToWishConverter(IPatientDtoToPatientConverter dtoToPatientConverter, IUserDtoToUserConverter userDtoToUserConverter) {
        this.dtoToPatientConverter = dtoToPatientConverter;
        this.userDtoToUserConverter = userDtoToUserConverter;
    }

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
