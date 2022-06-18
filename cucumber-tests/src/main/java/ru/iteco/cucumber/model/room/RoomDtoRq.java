package ru.iteco.cucumber.model.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ApiModel(description = "палата")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDtoRq {
    @ApiModelProperty("название комнаты")
    String name;
    @ApiModelProperty("Количество свободных мест")
    int maxOccupancy;
    @ApiModelProperty("комментарий")
    String comment;
    @ApiModelProperty("флаг удаления")
    boolean deleted;
}
