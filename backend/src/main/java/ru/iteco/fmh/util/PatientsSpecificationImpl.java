package ru.iteco.fmh.util;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.SearchPatientsDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.PatientStatus;

/**
 * Реализация интерфейса {@link PatientsSpecification} по созданию {@link Specification} для постраничного поиска пациентов.
 *
 * @author Viktor_Loskutov
 */
@Component
public class PatientsSpecificationImpl extends AbstractSpecification<Integer, Patient> implements PatientsSpecification {

    /**
     * Константа указывающая на название поля "id" в должности.
     */
    public static final String FIELD_ID_PATIENT = "id";

    /**
     * Константа указывающая на название поля "status" в должности.
     */
    public static final String FIELD_STATUS_PATIENT = "status";

    @Override
    public Specification<Patient> getSpecificationPatient(SearchPatientsDto searchPosition) {
        Specification<Patient> specification = Specification.where(null);
        return specification.and(spec(FIELD_STATUS_PATIENT, PatientStatus.valueOf(searchPosition.getStatus())));
    }
}