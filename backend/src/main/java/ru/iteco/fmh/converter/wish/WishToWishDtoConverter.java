package ru.iteco.fmh.converter.wish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.patient.PatientToPatientDtoIdFioConverter;
import ru.iteco.fmh.converter.user.UserToUserDtoIdFioConverter;
import ru.iteco.fmh.converter.room.RoomEntityToRoomDtoRsConverter;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.Room;
import ru.iteco.fmh.model.wish.Wish;
import ru.iteco.fmh.model.user.Role;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WishToWishDtoConverter implements Converter<Wish, WishDto> {
    private final RoomEntityToRoomDtoRsConverter roomEntityToRoomDtoRsConverter;
    private final PatientToPatientDtoIdFioConverter toPatientDtoIdFioConverter;
    private final UserToUserDtoIdFioConverter toUserDtoIdFioConverter;

    @Override
    public WishDto convert(@NonNull Wish wish) {
        WishDto dto = new WishDto();
        BeanUtils.copyProperties(wish, dto);
        List<Integer> roleIdsList = wish.getWishRoles().stream().map(Role::getId).toList();

        dto.setPatient(wish.getPatient() != null
                ? toPatientDtoIdFioConverter.convert(wish.getPatient()) : null);
        dto.setCreator(wish.getCreator() != null
                ? toUserDtoIdFioConverter.convert(wish.getCreator()) : null);
        Room patientRoom = wish.getPatient() != null ? wish.getPatient().getRoom() : null;
        RoomDtoRs roomDtoRs = patientRoom != null ? roomEntityToRoomDtoRsConverter.convert(patientRoom) : null;

        dto.setCreateDate(wish.getCreateDate() != null ? wish.getCreateDate().toEpochMilli() : null);
        dto.setPlanExecuteDate(wish.getPlanExecuteDate() != null ? wish.getPlanExecuteDate().toEpochMilli() : null);
        dto.setFactExecuteDate(wish.getFactExecuteDate() != null ? wish.getFactExecuteDate().toEpochMilli() : null);
        dto.setRoom(roomDtoRs);
        dto.setWishVisibility(roleIdsList);

        return dto;
    }
}


