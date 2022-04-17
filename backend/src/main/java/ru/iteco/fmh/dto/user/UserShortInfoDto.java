package ru.iteco.fmh.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "общая информация по пользователю")
@Builder
@Data
public class UserShortInfoDto {
    @ApiModelProperty("id пользователя")
    private Integer id;
    @ApiModelProperty("имя")
    private String firstName;
    @ApiModelProperty("фамилия")
    private String lastName;
    @ApiModelProperty("отчество")
    private String middleName;
    @ApiModelProperty("является ли администратором")
    private boolean isAdmin;

}
