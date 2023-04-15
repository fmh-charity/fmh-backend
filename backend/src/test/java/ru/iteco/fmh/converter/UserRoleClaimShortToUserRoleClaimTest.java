package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.user.UserRoleClaimShortToUserRoleClaim;
import ru.iteco.fmh.dto.user.UserRoleClaimShort;
import ru.iteco.fmh.model.user.RoleClaimStatus;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleClaimShortToUserRoleClaimTest {

    UserRoleClaimShortToUserRoleClaim target = new UserRoleClaimShortToUserRoleClaim();

    @Test
    void convert() {
        Random random = new Random();

        var testDTO = new UserRoleClaimShort(random.nextInt(5000), random.nextInt(5000), RoleClaimStatus.NEW);

        var actual = target.convert(testDTO);

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