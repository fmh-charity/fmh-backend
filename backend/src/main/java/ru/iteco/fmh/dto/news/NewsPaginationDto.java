package ru.iteco.fmh.dto.news;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class NewsPaginationDto {
    int pages;
    List<NewsDto> elements;
}
