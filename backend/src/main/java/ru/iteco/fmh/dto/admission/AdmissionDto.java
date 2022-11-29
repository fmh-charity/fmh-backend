package ru.iteco.fmh.dto.admission;

import static lombok.AccessLevel.PRIVATE;

import ru.iteco.fmh.model.admission.AdmissionsStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Schema(name = "Информация по госпитализации")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class AdmissionDto {

    @Schema(name = "id", description = "Идентификатор госпитализации")
    Integer id;

    @Schema(name = "patientId", description = "Идентификатор пациента")
    Integer patientId;

    //для сценария "Запланировать госпитализацию пациента"
    @Schema(name = "planDateIn", description = "Плановая дата поступления")
    Long planDateIn;
    @Schema(name = "planDateOut", description = "Плановая дата выписки")
    Long planDateOut;

    //для сценария "Госпитализация пациента"
    @Schema(name = "factDateIn", description = "Фактическая дата поступления")
    Long factDateIn;
    @Schema(name = "factDateOut", description = "Фактическая дата выписки")
    Long factDateOut;

    @Schema(name = "status", description = "Статус госпитализации")
    AdmissionsStatus status;

    @Schema(name = "roomId", description = "Идентификатор палаты")
    Integer roomId;

    @Schema(name = "comment", description = "Комментарий")
    String comment;
}