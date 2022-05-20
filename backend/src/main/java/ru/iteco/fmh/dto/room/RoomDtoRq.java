package ru.iteco.fmh.dto.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.Block;
import ru.iteco.fmh.model.NurseStation;

@ApiModel(description = "Палаты")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDtoRq {

    @ApiModelProperty("идентификатор палаты")
    private Integer id;

    @ApiModelProperty("название палаты")
    private String name;

    @ApiModelProperty("блок")
    private Block block;

    @ApiModelProperty("пост")
    private NurseStation nurseStation;

    @ApiModelProperty("количество доступных мест")
    private int maxOccupancy;

    @ApiModelProperty("комментарий")
    private String comment;

}
