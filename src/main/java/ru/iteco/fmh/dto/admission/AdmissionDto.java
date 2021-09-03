package ru.iteco.fmh.dto.admission;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Room;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import java.time.LocalDateTime;

@ApiModel(description = "информация по госпитализации")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionDto {
    @ApiModelProperty("идентификатор госпитализации")
    private int id;
    @ApiModelProperty("идентификатор пациента")
    private int patientId;

    //для сценария "Запланировать госпитализацию пациента"
    @ApiModelProperty("плановая дата поступления")
    private LocalDateTime planDateIn;
    @ApiModelProperty("плановая дата выписки")
    private LocalDateTime planDateOut;

    //для сценария "Госпитализация пациента"
    @ApiModelProperty(value = "фактическая дата поступления")
    private LocalDateTime factDateIn;
    @ApiModelProperty(value = "фактическая дата выписки")
    private LocalDateTime factDateOut;

    @ApiModelProperty(value = "статус госпитализации")
    AdmissionsStatus status;
    @ApiModelProperty(value = "идентификатор палаты")
    int roomId;
    @ApiModelProperty(value = "комментарий")
    private String comment;
}
