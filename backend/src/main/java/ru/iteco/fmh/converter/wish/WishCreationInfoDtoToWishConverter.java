package ru.iteco.fmh.converter.wish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.wish.WishCreationInfoDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.user.User;


import static ru.iteco.fmh.model.task.Status.OPEN;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class WishCreationInfoDtoToWishConverter implements Converter<WishCreationInfoDto, Wish> {
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Override
    public Wish convert(@NonNull WishCreationInfoDto dto) {
        Wish wish = new Wish();
        BeanUtils.copyProperties(dto, wish);

        Patient patient = dto.getPatientId() != null ? patientRepository.findPatientById(dto.getPatientId()) : null;
        User creator = dto.getCreatorId() != null ? userRepository.findUserById(dto.getCreatorId()) : null;
        User executor = dto.getExecutorId() != null ? userRepository.findUserById(dto.getExecutorId()) : null;

        wish.setCreateDate(dto.getCreateDate() != null ? Instant.ofEpochMilli(dto.getCreateDate()) : null);
        wish.setPlanExecuteDate(dto.getPlanExecuteDate() != null ? Instant.ofEpochMilli(dto.getPlanExecuteDate()) : null);
        wish.setStatus(OPEN);

        wish.setPatient(patient);
        wish.setCreator(creator);
        wish.setExecutor(executor);

        return wish;
    }
}
