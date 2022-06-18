package ru.iteco.fmh.converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<I, O, E> {
    O toDto(E entity);

    E toEntity(I dto);

    default List<O> mapToDto(Collection<E> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
