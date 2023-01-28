package ru.iteco.fmh.service.user;

import ru.iteco.fmh.dto.user.UserRoleClaimFull;
import ru.iteco.fmh.dto.user.UserRoleClaimShort;
import ru.iteco.fmh.model.user.RoleClaimStatus;
import ru.iteco.fmh.model.user.UserRoleClaim;

/**
 * Сервис для работы с заявками на роли для пользователя
 */
public interface UserRoleClaimService {
    public UserRoleClaimFull create(UserRoleClaimShort claimDto);

    public UserRoleClaimFull update(int id, RoleClaimStatus status);

    public UserRoleClaimFull update(int id, UserRoleClaimShort claimDto);
}
