package ru.iteco.fmh.converter.room;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.room.RoomDtoRq;
import ru.iteco.fmh.model.Room;

@Component
public class RoomRqToRoomConverter implements Converter<RoomDtoRq, Room> {
    @Override
    public Room convert(RoomDtoRq source) {
        return Room.builder()
                .name(source.getName())
                .comment(source.getComment())
                .maxOccupancy(source.getMaxOccupancy())
                .build();
    }
}
