package ru.iteco.fmh.dto.pagination;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PaginationDto {
    int count;
    List<PaginationInterface> elements;
}
