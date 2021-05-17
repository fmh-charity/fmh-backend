package ru.iteco.fmh.dto.admission;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.dto.patient.PatientDto;

import java.time.LocalDate;

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
    private LocalDate planDateIn;
    @ApiModelProperty("плановая дата выписки")
    private LocalDate planDateOut;

    //для сценария "Госпитализация пациента"
    @ApiModelProperty(value = "фактическая дата поступления")
    private LocalDate factDateIn;
    @ApiModelProperty(value = "фактическая дата выписки")
    private LocalDate factDateOut;

    @ApiModelProperty(value = "комментарий")
    private String comment;
}
