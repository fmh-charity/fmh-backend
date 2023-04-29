package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.user.UserRoleClaimShortToUserRoleClaim;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dto.user.UserRoleClaimShort;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.RoleClaimStatus;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static ru.iteco.fmh.TestUtils.*;

class UserRoleClaimShortToUserRoleClaimTest {
    RoleRepository roleRepository = mock(RoleRepository.class);

    UserRoleClaimShortToUserRoleClaim target = new UserRoleClaimShortToUserRoleClaim();

    @Test
    void convert() {
        var testDTO = new UserRoleClaimShort(1, 1, RoleClaimStatus.NEW);
        Role role = getRole("ROLE_ADMINISTRATOR");

        when(roleRepository.findRoleById(any())).thenReturn(role);

        var actual = target.convert(testDTO);

        assert actual != null;
        actual.setRole(roleRepository.findRoleById(testDTO.getRoleId()));

        assertAll(
                () -> assertEquals(testDTO.getUserId(), actual.getUserId()),
                () -> assertEquals(testDTO.getRoleId(), actual.getRole().getId()),
                () -> assertEquals(testDTO.getStatus(), actual.getStatus()),
                () -> assertNull(actual.getId()),
                () -> assertNull(actual.getCreatedAt()),
                () -> assertNull(actual.getUpdatedAt())
        );
    }
}