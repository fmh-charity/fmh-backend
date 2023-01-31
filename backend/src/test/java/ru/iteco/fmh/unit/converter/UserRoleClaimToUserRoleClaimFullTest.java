package ru.iteco.fmh.unit.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.user.UserRoleClaimToUserRoleClaimFull;
import ru.iteco.fmh.model.user.RoleClaimStatus;
import ru.iteco.fmh.model.user.UserRoleClaim;

import java.time.Instant;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRoleClaimToUserRoleClaimFullTest {

    UserRoleClaimToUserRoleClaimFull target = new UserRoleClaimToUserRoleClaimFull();

    @Test
    void convert() {
        Random random = new Random();

        var testEntity = new UserRoleClaim(
                random.nextInt(5000),
                random.nextInt(5000),
                random.nextInt(5000),
                RoleClaimStatus.NEW,
                Instant.MAX,
                Instant.now());

        var actual = target.convert(testEntity);

        assertAll(
                () -> assertEquals(testEntity.getId(), actual.getId()),
                () -> assertEquals(testEntity.getUserId(), actual.getUserId()),
                () -> assertEquals(testEntity.getRoleId(), actual.getRoleId()),
                () -> assertEquals(testEntity.getStatus(), actual.getStatus()),
                () -> assertEquals(testEntity.getCreatedAt(), actual.getCreatedAt()),
                () -> assertEquals(testEntity.getUpdatedAt(), actual.getUpdatedAt())
        );
    }
}