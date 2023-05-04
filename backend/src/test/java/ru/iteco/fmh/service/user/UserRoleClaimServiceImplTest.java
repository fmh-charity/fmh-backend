package ru.iteco.fmh.service.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.UserRoleClaimRepository;
import ru.iteco.fmh.dto.user.UserRoleClaimFull;
import ru.iteco.fmh.dto.user.UserRoleClaimShort;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.user.RoleClaimStatus;
import ru.iteco.fmh.model.user.UserRoleClaim;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;

@ExtendWith({MockitoExtension.class})
class UserRoleClaimServiceImplTest {

    @InjectMocks
    @Spy
    UserRoleClaimServiceImpl subj;

    @Mock
    UserRoleClaimRepository repository;
    @Mock
    ConversionService conversionService;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;

    @Test
    void create() {
        var user = TestUtils.getUser(TestUtils.getProfile());
        var claimDtoShort = new UserRoleClaimShort(user, 456, RoleClaimStatus.CONFIRMED);
        var claimConverted = new UserRoleClaim()
                .setUser(user)
                .setRole(TestUtils.getRole("ADMIN"))
                .setStatus(RoleClaimStatus.CONFIRMED);
        var claim = new UserRoleClaim()
                .setId(1000)
                .setUser(user)
                .setRole(TestUtils.getRole("ADMIN"))
                .setStatus(RoleClaimStatus.CONFIRMED)
                .setCreatedAt(Instant.now())
                .setUpdatedAt(Instant.now());
        var claimDtoFull = new UserRoleClaimFull(1000, 123, 456,
                RoleClaimStatus.CONFIRMED, claim.getCreatedAt(), claim.getUpdatedAt());


        var expected = claimDtoFull;
        doReturn(true).when(roleRepository).existsById(claimDtoShort.getRoleId());
        doReturn(claimConverted).when(conversionService).convert(claimDtoShort, UserRoleClaim.class);
        doReturn(claimDtoFull).when(conversionService).convert(claim, UserRoleClaimFull.class);
        doReturn(claim).when(repository).save(claimConverted);

        var actual = subj.create(claimDtoShort);

        assertEquals(actual, expected);
    }

    @ParameterizedTest(name = "{index} => userExist={0}, roleExist={1}")
    @CsvSource({
            "false, true",
            "true, false",
    })
    void createFail(boolean userExist, boolean roleExist) {
        var user = TestUtils.getUser(TestUtils.getProfile());
        var claimDtoShort = new UserRoleClaimShort(user, 456, RoleClaimStatus.CONFIRMED);
        lenient().doReturn(roleExist).when(roleRepository).existsById(claimDtoShort.getRoleId());

        assertThrows(NotFoundException.class, () -> subj.create(claimDtoShort));
    }

    @Test
    void updateWithDto() {
        var user = TestUtils.getUser(TestUtils.getProfile());
        var id =1000;
        var claimDtoShort = new UserRoleClaimShort(user, 456, RoleClaimStatus.CONFIRMED);
        var claimFromDB = new UserRoleClaim()
                .setId(id)
                .setUser(user)
                .setRole(TestUtils.getRole("ADMIN"))
                .setStatus(RoleClaimStatus.NEW)
                .setCreatedAt(Instant.now().minusSeconds(1000000));
        var claim = new UserRoleClaim()
                .setId(id)
                .setUser(user)
                .setRole(TestUtils.getRole("ADMIN"))
                .setStatus(RoleClaimStatus.CONFIRMED)
                .setCreatedAt(claimFromDB.getCreatedAt())
                .setUpdatedAt(Instant.now());
        var claimDtoFull = new UserRoleClaimFull(id, 123, 456,
                RoleClaimStatus.CONFIRMED, claim.getCreatedAt(), claim.getUpdatedAt());


        var expected = claimDtoFull;

        doReturn(true).when(roleRepository).existsById(claimDtoShort.getRoleId());
        doReturn(Optional.of(claimFromDB)).when(repository).findById(id);
        doReturn(claim).when(repository).save(claimFromDB);
        doReturn(claimDtoFull).when(conversionService).convert(claim, UserRoleClaimFull.class);

        var actual = subj.update(id, claimDtoShort);

        assertEquals(actual, expected);
    }

    @ParameterizedTest(name = "{index} => userExist={0}, roleExist={1}, roleClaimExist={2}")
    @CsvSource({
            "false, true, true",
            "true, false, true",
            "true, true, false",
    })
    void updateWithDtoFail(boolean userExist, boolean roleExist, boolean claimExist) {
        var id = 1000;
        var user = TestUtils.getUser(TestUtils.getProfile());
        var claimDtoShort = new UserRoleClaimShort(user, 456, RoleClaimStatus.CONFIRMED);
        UserRoleClaim claimFromDB = null;
        if (claimExist) claimFromDB = new UserRoleClaim();

        lenient().doReturn(roleExist).when(roleRepository).existsById(claimDtoShort.getRoleId());
        lenient().doReturn(Optional.ofNullable(claimFromDB)).when(repository).findById(id);

        assertThrows(NotFoundException.class, () -> subj.update(id, claimDtoShort));
    }

    @Test
    void updateWithStatus() {
        var id = 1000;
        var user = TestUtils.getUser(TestUtils.getProfile());
        var claimFromDB = new UserRoleClaim()
                .setId(id)
                .setUser(user)
                .setRole(TestUtils.getRole("ADMIN"))
                .setStatus(RoleClaimStatus.NEW)
                .setCreatedAt(Instant.now().minusSeconds(1000000));
        var claim = new UserRoleClaim()
                .setId(id)
                .setUser(user)
                .setRole(TestUtils.getRole("ADMIN"))
                .setStatus(RoleClaimStatus.CONFIRMED)
                .setCreatedAt(claimFromDB.getCreatedAt())
                .setUpdatedAt(Instant.now());
        var claimDtoFull = new UserRoleClaimFull(id, 123, 456,
                RoleClaimStatus.CONFIRMED, claim.getCreatedAt(), claim.getUpdatedAt());


        var expected = claimDtoFull;

        doReturn(Optional.of(claimFromDB)).when(repository).findById(id);
        doReturn(claim).when(repository).save(claimFromDB);
        doReturn(claimDtoFull).when(conversionService).convert(claim, UserRoleClaimFull.class);


        var actual = subj.update(id, RoleClaimStatus.CONFIRMED);

        assertEquals(actual, expected);
    }

    @Test
    void updateWithStatusFail() {
        var id = 1000;
        var status = RoleClaimStatus.CONFIRMED;

        doReturn(Optional.empty()).when(repository).findById(id);

        assertThrows(NotFoundException.class, () -> subj.update(id, status));
    }
}