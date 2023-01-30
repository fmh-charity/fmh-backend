package ru.iteco.fmh.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static ru.iteco.fmh.util.PatientsSpecificationImpl.FIELD_ID_PATIENT;
import static ru.iteco.fmh.util.PatientsSpecificationImpl.FIELD_STATUS_PATIENT;

/**
 * Реализация интерфейса {@link PatientsPageable} по созданию {@link PageRequest} для постраничного поиска пациентов.
 *
 * @author Viktor_Loskutov
 */
@Component
public class PatientsPageableImpl extends AbstractPageable implements PatientsPageable {

    @Override
    public PageRequest getPageRequest(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                sort(pageable.getSort(), FIELD_ID_PATIENT, FIELD_STATUS_PATIENT));
    }
}