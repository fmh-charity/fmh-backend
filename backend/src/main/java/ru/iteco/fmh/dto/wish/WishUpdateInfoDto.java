package ru.iteco.fmh.dto.wish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.dto.patient.PatientDtoIdFio;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.model.task.Status;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Schema(description = "Просьба")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class WishUpdateInfoDto {

    @Schema(name = "patient", description = "Идентификатор пациента")
    Integer patieentId;

    @Schema(name = "title", description = "Тема просьбы")
    String title;

    @Schema(name = "executor", description = "Идентификатор исполнителя")
    Integer executorId;

    @Schema(name = "description", description = "Описание записки")
    String description;

    @Schema(name = "planExecuteDate", description = "Плановая дата исполнения")
    Long planExecuteDate;

    @Schema(name = "room", description = "Палата пациента")
    Integer roomId;

    @Schema(name = "wishVisibility", description = "Область видимости")
    List<Integer> wishVisibility;
}