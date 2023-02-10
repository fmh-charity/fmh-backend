package ru.iteco.fmh.dto.document;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DocumentInfoPaginationDto {
    int pages;
    List<DocumentInfoDto> elements;
}
