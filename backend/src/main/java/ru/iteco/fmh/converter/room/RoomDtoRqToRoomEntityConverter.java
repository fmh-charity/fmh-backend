package ru.iteco.fmh.converter.room;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.NurseStationRepository;
import ru.iteco.fmh.dto.room.RoomDtoRq;
import ru.iteco.fmh.model.Room;

@Component
@RequiredArgsConstructor
public class RoomDtoRqToRoomEntityConverter implements Converter<RoomDtoRq, Room> {

    private final NurseStationRepository nurseStationRepository;

    @Override
    public Room convert(RoomDtoRq roomDto) {
        return Room.builder()
                .name(roomDto.getName())
                .nurseStation(nurseStationRepository
                        .findById(roomDto.getNurseStationId())
                        .orElseThrow(() -> new IllegalArgumentException("Пост с таким ID не существует")))
                .maxOccupancy(roomDto.getMaxOccupancy())
                .comment(roomDto.getComment())
                .build();
    }

}
