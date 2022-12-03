package ru.iteco.fmh.dto.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "Палаты")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDtoRq {

    @NotBlank
    @ApiModelProperty("название палаты")
    private String name;

    @ApiModelProperty("пост")
    private int nurseStationId;

    @ApiModelProperty("количество доступных мест")
    private int maxOccupancy;

    @ApiModelProperty("комментарий")
    private String comment;

}
