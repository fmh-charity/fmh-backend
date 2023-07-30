package ru.iteco.fmh.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Condition {
    private Comparision comparision;
    private Object value;
}
