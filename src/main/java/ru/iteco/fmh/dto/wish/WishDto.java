package ru.iteco.fmh.dto.wish;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.task.StatusE;

import java.time.LocalDateTime;

@ApiModel(description = "просьба")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WishDto {
    @ApiModelProperty("идентификатор записки")
    private Integer id;
    @ApiModelProperty("идентификатор пациента")
    private PatientDto patient;
    @ApiModelProperty("тема просьбы")
    private String title;

    @ApiModelProperty("описание записки")
    private String description;
    @ApiModelProperty("идентификатор создателя")
    private UserDto creator;
    @ApiModelProperty("идентификатор исполнителя")
    private UserDto executor;
    @ApiModelProperty("дата создания")
    private LocalDateTime createDate;
    @ApiModelProperty("плановая дата исполнения")
    private LocalDateTime planExecuteDate;
    @ApiModelProperty("фактическая дата исполнения")
    private LocalDateTime factExecuteDate;
    @ApiModelProperty("статус записки")
    private StatusE status;
}
