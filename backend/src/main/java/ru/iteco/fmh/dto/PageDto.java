package ru.iteco.fmh.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Представление модели страницы.
 *
 * @author Viktor_Loskutov
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@Schema(description = "Представление модели страницы")
public class PageDto<T extends Serializable> implements Serializable {

    @Schema(name = "data", description = "Список элементов")
    private List<T> data;

    @Schema(name = "total", description = "Общее количество элементов", example = "10")
    private long total;

    @Schema(name = "page", description = "Номер страницы", example = "1")
    private int page;
}