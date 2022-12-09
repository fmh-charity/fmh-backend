package ru.iteco.fmh.dto.wish;

import static lombok.AccessLevel.PRIVATE;

import ru.iteco.fmh.dto.patient.PatientDtoIdFio;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.model.task.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Schema(description = "Просьба")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class WishDto {

    @Schema(name = "id", description = "Идентификатор записки")
    Integer id;

    @Schema(name = "id", description = "Идентификатор пациента")
    PatientDtoIdFio patient;

    @Schema(name = "id", description = "Тема просьбы")
    String title;

    @Schema(name = "id", description = "Описание записки")
    String description;

    @Schema(name = "id", description = "Идентификатор создателя")
    Integer creatorId;

    @Schema(name = "id", description = "Идентификатор исполнителя")
    UserDtoIdFio executor;

    @Schema(name = "id", description = "Дата создания")
    Long createDate;

    @Schema(name = "id", description = "Плановая дата исполнения")
    Long planExecuteDate;

    @Schema(name = "id", description = "Фактическая дата исполнения")
    Long factExecuteDate;

    @Schema(name = "id", description = "Статус записки")
    Status status;

    @Schema(name = "id", description = "Палата пациента")
    RoomDtoRs room;

    @Schema(name = "id", description = "Область видимости")
    List<Integer> wishVisibility;
}