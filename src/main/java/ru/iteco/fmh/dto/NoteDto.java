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
    @ApiModelProperty("исполнитель, в формате \"Кузнецова Н.П.\"")
    private String executor;
    @ApiModelProperty("описание записки")
    private String description;
    @ApiModelProperty("плановое время выполнения")
    private LocalDateTime planExecuteDate;
    @ApiModelProperty("признак актуальности записки")
    private Boolean isActual;
}
