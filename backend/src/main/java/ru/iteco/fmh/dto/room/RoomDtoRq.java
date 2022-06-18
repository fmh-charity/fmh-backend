package ru.iteco.fmh.dto.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.Block;
import ru.iteco.fmh.model.NurseStation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@ApiModel(description = "палата")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDtoRq {

    @ApiModelProperty("id")
    Integer id;
    @NotBlank
    @ApiModelProperty("название комнаты")
    String name;
    @ApiModelProperty("Количество свободных мест")
    int maxOccupancy;
    @ApiModelProperty("комментарий")
    String comment;
    @ApiModelProperty("флаг удаления")
    boolean deleted;

}
