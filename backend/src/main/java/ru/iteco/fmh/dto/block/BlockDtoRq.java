package ru.iteco.fmh.dto.block;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.dto.room.RoomDtoRq;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@ApiModel(description = "блок")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlockDtoRq {

    @ApiModelProperty("название блока")
    @NotEmpty
    private String name;

    @ApiModelProperty("комментарий")
    private String comment;

    @ApiModelProperty("палаты")
    private Set<RoomDtoRq> roomDtoRqSet;

    @ApiModelProperty("флаг удаления")
    boolean deleted;

}
