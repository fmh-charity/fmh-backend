package ru.iteco.fmh.dto.post;

import static lombok.AccessLevel.PRIVATE;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Schema(name = "Информация о посте")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class NurseStationDtoRs {

    @Schema(name = "id", description = "Идентификатор поста")
    Integer id;

    @Schema(name = "name", description = "Название поста")
    String name;

    @Schema(name = "comment", description = "Комментарий")
    String comment;
}