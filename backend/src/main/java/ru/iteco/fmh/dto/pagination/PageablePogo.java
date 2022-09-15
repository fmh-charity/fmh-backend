package ru.iteco.fmh.dto.pagination;

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
    private int page = 0;
    private int elements = 8;
    private String status = "";
    private String typeOfSort = "";
}
