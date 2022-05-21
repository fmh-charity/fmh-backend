package ru.iteco.fmh.dto.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Информация поста")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NurseStationDtoRq {

    @ApiModelProperty("название")
    private String name;
    @ApiModelProperty("комментарий")
    private String comment;

}
