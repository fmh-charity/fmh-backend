package ru.iteco.fmh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel(description = "краткая информация по записке")
@Builder
@Data
public class NoteShortInfoDto {
    @ApiModelProperty("идентификатор записки")
    private Integer id;
    @ApiModelProperty("плановое время выполнения")
    private LocalDateTime planExecuteDate;
    @ApiModelProperty("фактическое время выполнения")
    private LocalDateTime factExecuteDate;
    @ApiModelProperty("ФИО пациента, в формате \"Кузнецова Н.П.\"")
    private String patient;
    @ApiModelProperty("исполнитель, в формате \"Кузнецова Н.П.\"")
    private String executor;
    @ApiModelProperty("статус записки")
    private String status;
}
