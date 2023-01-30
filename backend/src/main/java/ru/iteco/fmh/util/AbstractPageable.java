package ru.iteco.fmh.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Objects;

/**
 * Класс содержит методы дополнения {@link Sort} для {@link PageRequest} постраничного поиска сущностей.
 *
 * @author Viktor_Loskutov
 */
public abstract class AbstractPageable {

    /**
     * Дополняет сортировку заданными полями.
     *
     * @param sort         {@link Sort} сортировка
     * @param defaultField поле по-умолчанию в сущности, по которому будет производиться сортировка
     * @param fields       набор полей в сущности, которые дополняют сортировку
     * @return {@link Sort} сортировка
     */
    public Sort sort(Sort sort, String defaultField, String... fields) {
        return Arrays.stream(fields)
                .map(sort::getOrderFor)
                .filter(Objects::nonNull)
                .map(order -> addSortById(sort, order, defaultField))
                .findFirst()
                .orElse(sort);
    }

    /**
     * Выполняет сортировку по уникальному идентификатору, когда присутствуют дополнительное
     * поле для сортировки
     *
     * @param sort         сортировка по дополнительному полю
     * @param order        содержит список сортировок
     * @param defaultField поле сортировки по-умолчанию для сущности
     * @return сортировка, включающая основное и дополнительное поля.
     */
    private static Sort addSortById(Sort sort, Sort.Order order, String defaultField) {
        return sort.and(Sort.by(new Sort.Order(order.getDirection(), defaultField)));
    }
}