package ru.iteco.fmh.converter.post;

import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.post.NurseStationDto;
import ru.iteco.fmh.dto.post.NurseStationDtoRq;
import ru.iteco.fmh.dto.post.NurseStationDtoRs;
import ru.iteco.fmh.model.NurseStation;

import javax.validation.constraints.NotNull;

@Component
public class NurseStationConverter {

    public NurseStation toEntity(@NotNull NurseStationDto dto) {
        NurseStation entity = new NurseStation();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setComment(dto.getComment());
        return entity;
    }

    public NurseStationDto toDto(@NotNull NurseStation entity) {
        return NurseStationDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .comment(entity.getComment())
                .build();
    }

    public NurseStation rqToEntity(@NotNull NurseStationDtoRq dto) {
        NurseStation entity = new NurseStation();
        entity.setName(dto.getName());
        entity.setComment(dto.getComment());
        return entity;
    }

    public NurseStationDtoRs toRsDto(@NotNull NurseStation entity) {
        return NurseStationDtoRs.builder()
                .id(entity.getId())
                .name(entity.getName())
                .comment(entity.getComment())
                .build();
    }

}
