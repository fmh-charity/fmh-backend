package ru.iteco.fmh.dto.document;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DocumentForAdminPaginationRs {
    int pages;
    List<DocumentForAdminRs> elements;
}
