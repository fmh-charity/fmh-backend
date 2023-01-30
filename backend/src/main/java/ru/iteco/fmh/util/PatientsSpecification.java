package ru.iteco.fmh.util;

import org.springframework.data.jpa.domain.Specification;
import ru.iteco.fmh.dto.patient.SearchPatientsDto;
import ru.iteco.fmh.model.Patient;

/**
 * Создание {@link Specification} для постраничного поиска пациентов.
 *
 * @author Viktor_Loskutov
 */
public interface PatientsSpecification {

    /**
     * Получение {@link Specification} для запроса пациентов.
     *
     * @param searchPosition {@link SearchPatientsDto} с параметрами для поиска через {@link Specification}.
     * @return сформированная {@link Specification} для запроса.
     */
    Specification<Patient> getSpecificationPatient(SearchPatientsDto searchPosition);
}