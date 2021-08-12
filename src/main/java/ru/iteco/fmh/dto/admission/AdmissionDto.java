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
@AllArgsConstructor//generates a constructor with 1 parameter for each field in your class
@NoArgsConstructor//will generate a constructor with no parameters
public class AdmissionDto {
    @ApiModelProperty("идентификатор госпитализации")
    private Integer id;
    @ApiModelProperty("пациент")
    private PatientDto patient;

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
    @ApiModelProperty(value = "палата")
    Room room;
    @ApiModelProperty(value = "комментарий")
    private String comment;
}
