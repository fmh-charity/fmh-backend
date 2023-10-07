package ru.iteco.fmh.converter.wish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.WishExecutorsRepository;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.wish.Wish;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.model.wish.WishExecutor;

import java.time.Instant;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class WishDtoToWishConverter implements Converter<WishDto, Wish> {
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final WishExecutorsRepository wishExecutorsRepository;

    @Override
    public Wish convert(@NonNull WishDto dto) {
        Wish entity = new Wish();
        BeanUtils.copyProperties(dto, entity);

        Patient patient = dto.getPatient() != null ? patientRepository.findPatientById(dto.getPatient().id()) : null;
        User creator = dto.getCreator() != null ? userRepository.findUserById(dto.getCreator().id()) : null;
        Set<WishExecutor> executors = dto.getWishExecutors() != null ? wishExecutorsRepository.findAllByWishId(dto.getId()) : null;

        entity.setCreateDate(dto.getCreateDate() != null ? Instant.ofEpochMilli(dto.getCreateDate()) : null);
        entity.setPlanExecuteDate(dto.getPlanExecuteDate() != null ? Instant.ofEpochMilli(dto.getPlanExecuteDate()) : null);
        entity.setFactExecuteDate(dto.getFactExecuteDate() != null ? Instant.ofEpochMilli(dto.getFactExecuteDate()) : null);

        entity.setPatient(patient);
        entity.setCreator(creator);
        entity.setExecutors(executors);
        return entity;
    }
}

