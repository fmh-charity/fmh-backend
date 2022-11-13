package ru.iteco.fmh.converter.wish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.room.RoomEntityToRoomDtoRsConverter;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.Room;
import ru.iteco.fmh.model.task.wish.Wish;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WishToWishDtoConverter implements Converter<Wish, WishDto> {
    private final RoomEntityToRoomDtoRsConverter roomEntityToRoomDtoRsConverter;

    @Override
    public WishDto convert(@NonNull Wish wish) {
        WishDto dto = new WishDto();
        BeanUtils.copyProperties(wish, dto);

        Integer patientId = wish.getPatient() != null ? wish.getPatient().getId() : null;
        Integer creatorId = wish.getCreator() != null ? wish.getCreator().getId() : null;
        Integer executorId = wish.getExecutor() != null ? wish.getExecutor().getId() : null;
        Room patientRoom = wish.getPatient() != null ? wish.getPatient().getCurrentAdmission().getRoom() : null;
        RoomDtoRs roomDtoRs = patientRoom != null ? roomEntityToRoomDtoRsConverter.convert(patientRoom) : null;

        dto.setCreateDate(wish.getCreateDate() != null ? wish.getCreateDate().toEpochMilli() : null);
        dto.setPlanExecuteDate(wish.getPlanExecuteDate() != null ? wish.getPlanExecuteDate().toEpochMilli() : null);
        dto.setFactExecuteDate(wish.getFactExecuteDate() != null ? wish.getFactExecuteDate().toEpochMilli() : null);

        dto.setPatientId(patientId);
        dto.setCreatorId(creatorId);
        dto.setExecutorId(executorId);
        dto.setRoom(roomDtoRs);

        return dto;
    }
}


