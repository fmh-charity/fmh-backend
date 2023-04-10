package ru.iteco.fmh.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.UserRoleClaimRepository;
import ru.iteco.fmh.dto.user.UserRoleClaimFull;
import ru.iteco.fmh.dto.user.UserRoleClaimShort;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.user.RoleClaimStatus;
import ru.iteco.fmh.model.user.UserRoleClaim;

import java.time.Instant;

/**
 * Сервис для работы с заявками на роли для пользователя
 */
@Service
@RequiredArgsConstructor
public class UserRoleClaimServiceImpl implements UserRoleClaimService {

    private final UserRoleClaimRepository userRoleClaimRepository;
    private final ConversionService conversionService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserRoleClaimFull create(UserRoleClaimShort claimDto) {

        if (!userRepository.existsById(claimDto.getUserId())) {
            throw new NotFoundException("Не найден пользователь с id = " + claimDto.getUserId());
        }
        if (!roleRepository.existsById(claimDto.getRoleId())) {
            throw new NotFoundException("Не найден роль с id = " + claimDto.getRoleId());
        }

        UserRoleClaim entity = conversionService.convert(claimDto, UserRoleClaim.class);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(entity.getCreatedAt());
        return conversionService.convert(userRoleClaimRepository.save(entity), UserRoleClaimFull.class);
    }

    @Override
    public UserRoleClaimFull update(int id, RoleClaimStatus status) {
        var entity = userRoleClaimRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Не найдено заявки на роль с id = " + id));
        entity.setStatus(status);
        entity.setUpdatedAt(Instant.now());
        return conversionService.convert(userRoleClaimRepository.save(entity), UserRoleClaimFull.class);
    }

    @Override
    public UserRoleClaimFull update(int id, UserRoleClaimShort claimDto) {
        if (!userRepository.existsById(claimDto.getUserId())) {
            throw new NotFoundException("Не найден пользователь с id = " + claimDto.getUserId());
        }
        if (!roleRepository.existsById(claimDto.getRoleId())) {
            throw new NotFoundException("Не найден роль с id = " + claimDto.getRoleId());
        }

        var entity = userRoleClaimRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Не найдено заявки на роль с id = " + id));
        var role = roleRepository.findById(claimDto.getRoleId()).get();

        entity.setUserId(claimDto.getUserId());
        entity.setRole(role);
        entity.setStatus(claimDto.getStatus());
        entity.setUpdatedAt(Instant.now());
        return conversionService.convert(userRoleClaimRepository.save(entity), UserRoleClaimFull.class);
    }
}
