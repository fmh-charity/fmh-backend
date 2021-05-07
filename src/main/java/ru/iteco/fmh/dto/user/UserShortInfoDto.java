package ru.iteco.fmh.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@ApiModel(description = "общая информация по пользователю")
@Builder
@Data
public class UserShortInfoDto {
    @ApiModelProperty("id пользователя")
    private Integer id;
    @ApiModelProperty("имя")
    private String name;
    @ApiModelProperty("фамилия")
    private String lastName;
    @ApiModelProperty("отчество")
    private String middleName;
    @ApiModelProperty("логин")
    private String login;
}
