package ru.iteco.fmh.dto.wish;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WishPaginationDto {
    int pages;
    List<WishDto> elements;
}
