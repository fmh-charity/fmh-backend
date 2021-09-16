package ru.iteco.fmh.dto.admission;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

@ApiModel(description = "информация по госпитализации")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionDto {
    @ApiModelProperty("идентификатор госпитализации")
    private int id;
    @ApiModelProperty("идентификатор пациента")
    private Integer patientId;

    //для сценария "Запланировать госпитализацию пациента"
    @ApiModelProperty("плановая дата поступления")
    private Long planDateIn;
    @ApiModelProperty("плановая дата выписки")
    private Long planDateOut;

    //для сценария "Госпитализация пациента"
    @ApiModelProperty(value = "фактическая дата поступления")
    private Long factDateIn;
    @ApiModelProperty(value = "фактическая дата выписки")
    private Long factDateOut;

    @ApiModelProperty(value = "статус госпитализации")
    AdmissionsStatus status;
    @ApiModelProperty(value = "идентификатор палаты")
    private Integer roomId;
    @ApiModelProperty(value = "комментарий")
    private String comment;
}
