package ru.iteco.cucumber.model.block;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlockDtoRs {

    private Integer id;

    private String name;

    private String comment;

    boolean deleted;
}