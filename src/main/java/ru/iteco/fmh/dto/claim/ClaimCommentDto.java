package ru.iteco.fmh.dto.claim;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "комментарий к заявке (request version)")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ClaimCommentDto {

    @ApiModelProperty("идентификатор комментария к заявке")
    private Integer id;
    @ApiModelProperty("идентификатор заявки к которой создан комментарий")
    private Integer claimId;
    @ApiModelProperty("описание комментария к заявке")
    private String description;
    @ApiModelProperty("идентификатор создателя комментария к заявке")
    private Integer creatorId;
    @ApiModelProperty("дата создания комментария к заявке")
    private Long createDate;
    @ApiModelProperty("ФИО создателя")
    private String creatorName;
}
