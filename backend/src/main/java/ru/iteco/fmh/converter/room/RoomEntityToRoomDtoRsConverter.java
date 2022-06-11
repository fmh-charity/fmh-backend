package ru.iteco.fmh.converter.room;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.model.Room;

import javax.validation.constraints.NotNull;

@Component
public class RoomEntityToRoomDtoRsConverter implements Converter<Room, RoomDtoRs> {

    @Override
    public RoomDtoRs convert(@NotNull Room entity) {
        return RoomDtoRs.builder()
                .id(entity.getId())
                .name(entity.getName())
                .nurseStationId(entity.getNurseStation().getId())
                .maxOccupancy(entity.getMaxOccupancy())
                .comment(entity.getComment())
                .build();
    }
}
