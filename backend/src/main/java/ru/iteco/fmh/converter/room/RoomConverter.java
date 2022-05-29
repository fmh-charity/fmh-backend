package ru.iteco.fmh.converter.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.BlockRepository;
import ru.iteco.fmh.dao.repository.NurseStationRepository;
import ru.iteco.fmh.dto.room.RoomDto;
import ru.iteco.fmh.dto.room.RoomDtoRq;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.model.Room;

import javax.validation.constraints.NotNull;

@Component
@RequiredArgsConstructor
public class RoomConverter {

    private final BlockRepository blockRepository;
    private final NurseStationRepository nurseStationRepository;

    public RoomDto toDto(@NotNull Room entity) {
        return RoomDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .blockId(entity.getBlock().getId())
                .nurseStationId(entity.getNurseStation().getId())
                .maxOccupancy(entity.getMaxOccupancy())
                .comment(entity.getComment())
                .build();
    }

    public RoomDtoRs toDtoRs(@NotNull Room entity) {
        return RoomDtoRs.builder()
                .id(entity.getId())
                .name(entity.getName())
                .blockId(entity.getBlock().getId())
                .nurseStationId(entity.getNurseStation().getId())
                .maxOccupancy(entity.getMaxOccupancy())
                .comment(entity.getComment())
                .build();
    }

    public Room toEntity(RoomDtoRq roomDto) {
        Room entity = new Room();
        entity.setId(roomDto.getId());
        entity.setName(roomDto.getName());
        entity.setBlock(
                blockRepository
                        .findById(roomDto.getBlockId())
                        .orElseThrow(() -> new IllegalArgumentException("Блок с таким ID не существует"))
        );
        entity.setNurseStation(
                nurseStationRepository
                        .findById(roomDto.getNurseStationId())
                        .orElseThrow(() -> new IllegalArgumentException("Пост с таким ID не существует"))
        );
        entity.setMaxOccupancy(roomDto.getMaxOccupancy());
        entity.setComment(roomDto.getComment());
        return entity;
    }
}
