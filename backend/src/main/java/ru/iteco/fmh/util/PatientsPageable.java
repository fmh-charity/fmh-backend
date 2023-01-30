package ru.iteco.fmh.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Создание {@link PageRequest} для постраничного поиска пациентов.
 *
 * @author Viktor_Loskutov
 */
public interface PatientsPageable {

    /**
     * Преобразует Pageable поля для сортировки.
     *
     * @param pageable {@link Pageable} с параметрами: номер страницы, кол-во элементов и тип сортировки.
     * @return {@link PageRequest} сформированный запрос для страницы.
     */
    PageRequest getPageRequest(Pageable pageable);
}