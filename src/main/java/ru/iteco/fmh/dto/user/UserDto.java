package ru.iteco.fmh.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@ApiModel(description = "информация по пользователю")
@Builder
@Data
@AllArgsConstructor//generates a constructor with 1 parameter for each field in your class
@NoArgsConstructor
public class UserDto {
    @ApiModelProperty("id пользователя")
    private Integer id;
    @ApiModelProperty("имя")
    private String firstName;
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
    private String email;
    @ApiModelProperty("ФИО пользователя, в формате \"Кузнецова Н.П.\"")
    private String shortUserName;
//    @ApiModelProperty("роли")
//    private List<String> roles;
}
