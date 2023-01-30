package ru.iteco.fmh.util;

import org.springframework.data.jpa.domain.Specification;
import ru.iteco.fmh.model.PatientStatus;

import java.io.Serializable;

/**
 * <p>Содержит утилитарные методы дополнения {@link Specification} для постраничного поиска сущностей.
 * <p>В качестве key выступают поля сущностей, а value - значения этого поля для определённого типа сравнения.
 * <p>Для использования этих методов необходимо наследоваться от данного класса,
 * указав класс первичного ключа сущности {@link K} и класс самой сущности {@link T},
 * для которой и будет подготавливаться {@link Specification}.
 * <p>Можно добавить несколько {@link Specification} к одной сущности при помощи
 * {@link Specification#and(Specification)}
 *
 * @param <K> первичный ключ
 * @param <T>  класс сущности
 *
 * @author @author Viktor_Loskutov
 */
public abstract class AbstractSpecification<K extends Serializable, T> {

    /**
     * Создание {@link Specification}.
     * Определяет, совпадает ли указанное значение поля с данным значением
     *
     * @param key   поле в сущности {@link T}
     * @param value значение поля
     * @return {@link Specification}
     */
    public Specification<T> spec(String key, Enum<PatientStatus> value) {
        return (root, query, criteriaBuilder) -> value == null ? null : root.get(String.valueOf(key)).in(value);
    }
}