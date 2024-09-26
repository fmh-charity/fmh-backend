package ru.iteco.fmh.dto.room;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Schema(description = "Палаты")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class RoomDtoRs {

    @Schema(name = "id", description = "Идентификатор палаты")
    Integer id;

    @Schema(name = "name", description = "Название палаты")
    String name;

    @Schema(name = "nurseStationId", description = "Идентификатор поста")
    Integer nurseStationId;

    @Schema(name = "maxOccupancy", description = "Количество доступных мест")
    Integer maxOccupancy;

    @Schema(name = "comment", description = "Комментарий")
    String comment;
}