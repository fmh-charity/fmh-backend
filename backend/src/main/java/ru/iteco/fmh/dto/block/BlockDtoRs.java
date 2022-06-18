package ru.iteco.fmh.dto.block;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "блок")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlockDtoRs {

    @ApiModelProperty("идентификатор блока")
    private Integer id;

    @ApiModelProperty("название блока")
    private String name;

    @ApiModelProperty("комментарий")
    private String comment;

    @ApiModelProperty("флаг удаления")
    boolean deleted;

}
