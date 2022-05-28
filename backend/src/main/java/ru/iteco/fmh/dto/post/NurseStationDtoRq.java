package ru.iteco.fmh.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.model.Room;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@ApiModel(description = "Информация поста")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NurseStationDtoRq {

    @NotBlank
    @ApiModelProperty("название")
    private String name;

    @ApiModelProperty("комментарий")
    private String comment;

    @JsonIgnore
    @ApiModelProperty("палаты")
    private Set<Room> rooms;

}
