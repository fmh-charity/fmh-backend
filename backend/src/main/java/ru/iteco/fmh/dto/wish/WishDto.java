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

    @Schema(name = "patient", description = "Идентификатор пациента")
    PatientDtoIdFio patient;

    @Schema(name = "title", description = "Тема просьбы")
    String title;

    @Schema(name = "description", description = "Описание записки")
    String description;

    @Schema(name = "creator", description = "Идентификатор создателя")
    UserDtoIdFio creator;

    @Schema(name = "executor", description = "Идентификатор исполнителя")
    UserDtoIdFio executor;

    @Schema(name = "createDate", description = "Дата создания")
    Long createDate;

    @Schema(name = "planExecuteDate", description = "Плановая дата исполнения")
    Long planExecuteDate;

    @Schema(name = "factExecuteDate", description = "Фактическая дата исполнения")
    Long factExecuteDate;

    @Schema(name = "status", description = "Статус записки")
    Status status;

    @Schema(name = "room", description = "Палата пациента")
    RoomDtoRs room;

    @Schema(name = "wishVisibility", description = "Область видимости")
    List<Integer> wishVisibility;
}