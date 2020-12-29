package ru.iteco.fmh.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel(description = "записка")
@Builder
@Data
public class NoteDto {
    @ApiModelProperty("идентификатор пациента")
    private Integer patientId;
    @ApiModelProperty("суть записки")
    private String description;
    @ApiModelProperty("плановое время исполнения")
    private LocalDateTime planExecuteTime;
    @ApiModelProperty("идентификатор исполнителя")
    private Integer executorId;
    @ApiModelProperty("комментарий к записке")
    private String comment;
}
