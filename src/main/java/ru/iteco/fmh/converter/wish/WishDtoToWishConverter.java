package ru.iteco.fmh.converter.wish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.user.User;

@Component
@RequiredArgsConstructor
public class WishDtoToWishConverter implements Converter<WishDto, Wish> {
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Override
    public Wish convert(@NonNull WishDto dto) {
        Wish entity = new Wish();
        BeanUtils.copyProperties(dto, entity);

        Patient patient = dto.getPatientId() != null ? patientRepository.findPatientById(dto.getPatientId()) : null;
        User creator = dto.getCreatorId() != null ? userRepository.findUserById(dto.getCreatorId()) : null;
        User executor = dto.getExecutorId() != null ? userRepository.findUserById(dto.getExecutorId()) : null;


        entity.setPatient(patient);
        entity.setCreator(creator);
        entity.setExecutor(executor);

        return entity;
    }
}

