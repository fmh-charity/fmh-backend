package ru.iteco.fmh.dto.claim;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ClaimPaginationDto {
    int pages;
    List<ClaimDto> elements;
}
