package ru.iteco.fmh.dto.claim;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iteco.fmh.dto.user.UserDto;

import java.time.LocalDateTime;

@ApiModel(description = "комментарий к заявке")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ClaimCommentDto {
    @ApiModelProperty("идентификатор комментария к заявке")
    private Integer id;
    @ApiModelProperty("идентификатор заявки к которой создан комментарий")
    private ClaimDto claim;
    @ApiModelProperty("описание комментария к заявке")
    private String description;
    @ApiModelProperty("идентификатор создателя комментария к заявке")
    private UserDto creator;
    @ApiModelProperty("дата создания комментария к заявке")
    private LocalDateTime createDate;
}
