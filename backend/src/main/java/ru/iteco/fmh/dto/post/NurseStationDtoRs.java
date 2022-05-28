package ru.iteco.fmh.dto.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.model.Room;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@ApiModel(description = "Информация поста")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NurseStationDtoRs {

    @ApiModelProperty("id поста")
    private Integer id;

    @NotBlank
    @ApiModelProperty("название")
    private String name;

    @ApiModelProperty("комментарий")
    private String comment;

    @ApiModelProperty("палаты")
    private Set<Room> rooms;

}
