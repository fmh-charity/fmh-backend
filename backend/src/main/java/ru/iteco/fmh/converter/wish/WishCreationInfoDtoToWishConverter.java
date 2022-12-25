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

import java.time.Instant;

import static ru.iteco.fmh.model.task.Status.IN_PROGRESS;
import static ru.iteco.fmh.model.task.Status.OPEN;

@Component
@RequiredArgsConstructor
public class WishCreationInfoDtoToWishConverter implements Converter<WishCreationInfoDto, Wish> {
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Override
    public Wish convert(@NonNull WishCreationInfoDto wishCreationInfoDto) {
        Wish wish = new Wish();
        BeanUtils.copyProperties(wishCreationInfoDto, wish);

        Patient patient = wishCreationInfoDto.getPatientId() != null ? patientRepository
                .findPatientById(wishCreationInfoDto.getPatientId()) : null;
        User executor = wishCreationInfoDto.getExecutorId() != null ? userRepository
                .findUserById(wishCreationInfoDto.getExecutorId()) : null;

        wish.setCreateDate(Instant.now());
        wish.setPlanExecuteDate(wishCreationInfoDto.getPlanExecuteDate() != null
                ? Instant.ofEpochMilli(wishCreationInfoDto.getPlanExecuteDate()) : null);
        wish.setStatus(executor == null ? OPEN : IN_PROGRESS);
        wish.setPatient(patient);
        wish.setExecutor(executor);

        return wish;
    }
}
