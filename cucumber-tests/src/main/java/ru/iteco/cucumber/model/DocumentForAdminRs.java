package ru.iteco.cucumber.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Date;

@ApiModel(description = "информация о документе для администратора")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentForAdminRs {
    @ApiModelProperty("id документа")
    Integer id;
    @ApiModelProperty("имя документа")
    String name;
    @ApiModelProperty("путь документа")
    String filePath;
    @ApiModelProperty("описание документа")
    String description;
    @ApiModelProperty("статус документа")
    DocumentStatus status;
    @ApiModelProperty("дата создания документа")
    Date createDate;
    @ApiModelProperty("id владельца документа")
    UserDtoIdFio user;
}
