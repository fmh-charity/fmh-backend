package ru.iteco.fmh.converter.wish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.patient.PatientToPatientDtoIdFioConverter;
import ru.iteco.fmh.converter.user.UserToUserDtoIdFioConverter;
import ru.iteco.fmh.dto.patient.PatientDtoIdFio;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;

@Component
@RequiredArgsConstructor
public class WishToWishDtoConverter implements Converter<Wish, WishDto> {

    private final PatientToPatientDtoIdFioConverter toPatientDtoIdFioConverter;
    private final UserToUserDtoIdFioConverter toUserDtoIdFioConverter;

    @Override
    public WishDto convert(@NonNull Wish wish) {
        WishDto dto = new WishDto();
        BeanUtils.copyProperties(wish, dto);

        dto.setPatient(wish.getPatient() != null
                ? toPatientDtoIdFioConverter.convert(wish.getPatient()) : null);
        dto.setCreatorId(wish.getCreator() != null ? wish.getCreator().getId() : null);
        dto.setExecutor(wish.getExecutor() != null
                ? toUserDtoIdFioConverter.convert(wish.getExecutor()) : null);

        dto.setCreateDate(wish.getCreateDate() != null ? wish.getCreateDate().toEpochMilli() : null);
        dto.setPlanExecuteDate(wish.getPlanExecuteDate() != null ? wish.getPlanExecuteDate().toEpochMilli() : null);
        dto.setFactExecuteDate(wish.getFactExecuteDate() != null ? wish.getFactExecuteDate().toEpochMilli() : null);

        return dto;
    }
}


