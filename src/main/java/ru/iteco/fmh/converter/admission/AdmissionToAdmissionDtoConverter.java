package ru.iteco.fmh.converter.admission;

import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.admission.Admission;

/**
 * конвертер из {@link Admission} в {@link AdmissionDto}
 */
@Component
public class AdmissionToAdmissionDtoConverter implements Converter<Admission, AdmissionDto> {
    @Override
    public AdmissionDto convert(@NonNull Admission admission) {
        AdmissionDto admissionDto = new AdmissionDto();
        BeanUtils.copyProperties(admission, admissionDto);

        admissionDto.setPatientId(admission.getPatient().getId());
        admissionDto.setRoomId(admission.getRoom().getId());

        return admissionDto;
    }
}
