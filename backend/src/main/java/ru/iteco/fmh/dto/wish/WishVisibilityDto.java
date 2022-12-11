package ru.iteco.fmh.dto.wish;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WishVisibilityDto {
    private int id;
    private String name;
}
