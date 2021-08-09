package ru.iteco.fmh.dto.wish;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.model.StatusE;

import java.time.LocalDateTime;

@ApiModel(description = "краткая информация по просьбе")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WishShortInfoDto {
    @ApiModelProperty("идентификатор записки")
    private Integer id;
    @ApiModelProperty("плановая дата исполнения")
    private LocalDateTime planExecuteDate;
    @ApiModelProperty("фактическая дата исполнения")
    private LocalDateTime factExecuteDate;
    @ApiModelProperty("ФИО пациента, в формате \"Кузнецова Н.П.\"")
    private String shortPatientName;
    @ApiModelProperty("исполнитель, в формате \"Кузнецова Н.П.\"")
    private String shortExecutorName;
    @ApiModelProperty("статус записки")
    private StatusE status;
    @ApiModelProperty("описание записки")
    private String description;
}
