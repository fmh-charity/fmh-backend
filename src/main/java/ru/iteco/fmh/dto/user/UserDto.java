package ru.iteco.fmh.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@ApiModel(description = "информация по пользователю")
@Builder
@Data
public class UserDto {
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
    @ApiModelProperty("пароль")
    private String password;
    @ApiModelProperty("телефон")
    private String phoneNumber;
    @ApiModelProperty("электронная почта")
    private String eMail;
    @ApiModelProperty("ФИО пользователя, в формате \"Кузнецова Н.П.\"")
    private String userShortName;
    @ApiModelProperty("роли")
    private List<String> roles;
}
