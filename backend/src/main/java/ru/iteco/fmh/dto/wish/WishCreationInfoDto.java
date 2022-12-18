package ru.iteco.fmh.dto.wish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Schema(description = "Просьба")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class WishCreationInfoDto {

    @Schema(name = "patientId", description = "Идентификатор пациента")
    Integer patientId;

    @Schema(name = "title", description = "Тема просьбы")
    String title;

    @Schema(name = "executorId", description = "Идентификатор исполнителя")
    Integer executorId;

    @Schema(name = "createDate", description = "Дата создания")
    Long createDate;

    @Schema(name = "planExecuteDate", description = "Плановая дата исполнения")
    Long planExecuteDate;

    @Schema(name = "description", description = "Описание записки")
    String description;

    @Schema(name = "wishVisibility", description = "Область видимости")
    List<Integer> wishVisibility;
}