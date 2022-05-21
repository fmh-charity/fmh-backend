package ru.iteco.cucumber.model;

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

    @ApiModelProperty("id поста")
    private Integer id;
    @ApiModelProperty("название")
    private String name;
    @ApiModelProperty("комментарий")
    private String comment;

}
