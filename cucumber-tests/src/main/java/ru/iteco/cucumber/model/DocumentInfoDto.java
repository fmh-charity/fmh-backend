package ru.iteco.cucumber.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ApiModel(description = "информация о дументе")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentInfoDto {

    @ApiModelProperty("id документа")
    Integer id;

    @ApiModelProperty("полный путь документа")
    String filePath;

    @ApiModelProperty("описание")
    String description;
}
