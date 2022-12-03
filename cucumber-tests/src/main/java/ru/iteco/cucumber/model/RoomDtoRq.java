package ru.iteco.cucumber.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ApiModel(description = "Палаты")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDtoRq {

    @ApiModelProperty("название палаты")
    private String name;

    @ApiModelProperty("пост")
    private int nurseStationId;

    @ApiModelProperty("количество доступных мест")
    private int maxOccupancy;

    @ApiModelProperty("комментарий")
    private String comment;

}
