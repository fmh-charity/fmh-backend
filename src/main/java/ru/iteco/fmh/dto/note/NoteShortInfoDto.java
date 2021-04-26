package ru.iteco.fmh.dto.note;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(description = "краткая информация по записке")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NoteShortInfoDto {
    @ApiModelProperty("идентификатор записки")
    private Integer id;
    @ApiModelProperty("плановое время выполнения")
    private LocalDateTime planExecuteTime;
    @ApiModelProperty("фактическое время выполнения")
    private LocalDateTime factExecuteTime;
    @ApiModelProperty("ФИО пациента, в формате \"Кузнецова Н.П.\"")
    private String shortPatientName;
    @ApiModelProperty("исполнитель, в формате \"Кузнецова Н.П.\"")
    private String shortExecutorName;
    @ApiModelProperty("статус записки")
    private String status;
}
