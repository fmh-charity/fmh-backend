package ru.iteco.fmh.dto.patient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
//непонятный класс.если не пригодиться - delete
@ApiModel(description = "записка по пациенту")
@Builder
@Data
public class PatientNoteDto {
    @ApiModelProperty("идентификатор записки")
    private Integer id;
    @ApiModelProperty("исполнитель, в формате \"Кузнецова Н.П.\"")
    private String executor;
    @ApiModelProperty("описание записки")
    private String description;
    @ApiModelProperty("плановое время выполнения")
    private LocalDateTime planExecuteDate;
    @ApiModelProperty("признак актуальности записки")
    private Boolean isActual;
}
