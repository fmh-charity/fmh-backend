package ru.iteco.fmh.dto.wish;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.dto.patient.PatientDtoIdFio;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.model.task.Status;

@ApiModel(description = "редактирование просьбы")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WishUpdateInfoDto {
    @ApiModelProperty("идентификатор пациента")
    private Integer patientId;
    @ApiModelProperty("тема просьбы")
    private String title;
    @ApiModelProperty("идентификатор исполнителя")
    private Integer executorId;
    @ApiModelProperty("плановая дата исполнения")
    private Long planExecuteDate;
    @ApiModelProperty("описание просьбы")
    private String description;
}
