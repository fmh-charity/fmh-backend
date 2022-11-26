package ru.iteco.fmh.dto.wish;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.dto.patient.PatientDtoIdFio;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.model.user.RoleName;

import java.util.List;

@ApiModel(description = "просьба")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WishDto {
    @ApiModelProperty("идентификатор записки")
    private Integer id;
    @ApiModelProperty("идентификатор пациента")
    private PatientDtoIdFio patient;
    @ApiModelProperty("тема просьбы")
    private String title;
    @ApiModelProperty("описание записки")
    private String description;
    @ApiModelProperty("идентификатор создателя")
    private Integer creatorId;
    @ApiModelProperty("идентификатор исполнителя")
    private UserDtoIdFio executor;
    @ApiModelProperty("дата создания")
    private Long createDate;
    @ApiModelProperty("плановая дата исполнения")
    private Long planExecuteDate;
    @ApiModelProperty("фактическая дата исполнения")
    private Long factExecuteDate;
    @ApiModelProperty("статус записки")
    private Status status;
    @ApiModelProperty("палата пациента")
    private RoomDtoRs room;
    @ApiModelProperty("область видимости")
    private List<RoleName> wishVisibility;
}
