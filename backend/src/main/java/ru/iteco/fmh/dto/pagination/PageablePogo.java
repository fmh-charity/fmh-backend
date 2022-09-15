package ru.iteco.fmh.dto.pagination;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageablePogo {
    @Min(value = 0) @Max(value = 20000)
    private int page = 0;
    @Min(value = 1) @Max(value = 200)
    private int elements = 8;
    private String status = "";
    private String typeOfSort = "";
}
