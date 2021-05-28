package ru.iteco.fmh.dto.claim;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.StatusE;

import java.time.LocalDateTime;

@ApiModel(description = "заявка")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ClaimDto {
    @ApiModelProperty("идентификатор заявки")
    private Integer id;
    @ApiModelProperty("описание заявки")
    private String description;
    @ApiModelProperty("идентификатор создателя")
    private UserDto creator;
    @ApiModelProperty("идентификатор исполнителя")
    private UserDto executor;
    @ApiModelProperty("дата создания заявки")
    private LocalDateTime createDate;
    @ApiModelProperty("плановая дата исполнения заявки")
    private LocalDateTime planExecuteDate;
    @ApiModelProperty("фактическая дата исполнения заявки")
    private LocalDateTime factExecuteDate;
    @ApiModelProperty("статус заявки")
    private StatusE status;
    @ApiModelProperty("комментарий к заявке")
    private String comment;
}


