package ru.iteco.fmh.converter.wish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.RoleName;
import ru.iteco.fmh.model.user.User;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WishDtoToWishConverter implements Converter<WishDto, Wish> {
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final RoleRepository roleRepository;

    @Override
    public Wish convert(@NonNull WishDto dto) {
        Wish entity = new Wish();
        BeanUtils.copyProperties(dto, entity);
        List<RoleName> roleNamesList = dto.getWishVisibility().stream().map(RoleName::valueOf).toList();
        List<Role> roleList = roleRepository.findAllByNameIn(roleNamesList);

        Patient patient = dto.getPatient() != null ? patientRepository.findPatientById(dto.getPatient().id()) : null;
        User creator = dto.getCreatorId() != null ? userRepository.findUserById(dto.getCreatorId()) : null;
        User executor = dto.getExecutor() != null ? userRepository.findUserById(dto.getExecutor().id()) : null;

        entity.setCreateDate(dto.getCreateDate() != null ? Instant.ofEpochMilli(dto.getCreateDate()) : null);
        entity.setPlanExecuteDate(dto.getPlanExecuteDate() != null ? Instant.ofEpochMilli(dto.getPlanExecuteDate()) : null);
        entity.setFactExecuteDate(dto.getFactExecuteDate() != null ? Instant.ofEpochMilli(dto.getFactExecuteDate()) : null);


        entity.setPatient(patient);
        entity.setCreator(creator);
        entity.setExecutor(executor);
        entity.setWishVisibility(roleList);
        return entity;
    }
}

