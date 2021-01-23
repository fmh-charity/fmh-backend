package ru.iteco.fmh.service;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.converter.AdmissionDtoToAdmissionConverter;
import ru.iteco.fmh.converter.AdmissionToAdmissionDtoConverter;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dto.AdmissionDto;
import ru.iteco.fmh.dto.PatientDto;
import ru.iteco.fmh.model.Admission;

import java.util.Optional;

/**
 * Реализация сервиса для работы с госпитализацией {@link AdmissionService}
 */
@Service
public class AdmissionServiceImpl implements AdmissionService {

    private AdmissionRepository admissionRepository;
    private AdmissionToAdmissionDtoConverter admissionDtoConverter;
    private AdmissionDtoToAdmissionConverter admissionConverter;

    public AdmissionServiceImpl(
            AdmissionRepository admissionRepository,
            AdmissionToAdmissionDtoConverter admissionDtoConverter,
            AdmissionDtoToAdmissionConverter admissionConverter
    ) {
        this.admissionRepository = admissionRepository;
        this.admissionDtoConverter = admissionDtoConverter;
        this.admissionConverter = admissionConverter;
    }

    @Override
    public AdmissionDto getAdmissionInfo(Integer id) {
        return admissionRepository.findById(id)
                .map(admissionDtoConverter::convert)
                .orElse(null);
    }

    @Override
    public Integer createOrUpdateAdmission(AdmissionDto admissionDto) {
        Admission admission = admissionConverter.convert(admissionDto);
        return admissionRepository.save(admission).getId();
    }
}
