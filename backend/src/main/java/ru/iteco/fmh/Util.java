package ru.iteco.fmh;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Set;

@RequiredArgsConstructor
public class Util {

    private static final String ADMIN_ROLE = "ROLE_ADMINISTRATOR";

    public static Instant getInstantFromLocalDateAtStartOfDay(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    public static Instant getInstantFromLocalDateToEndOfDay(LocalDate localDate) {
        return localDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();
    }

    public static String getCreatorName(User user) {
        return user.getLastName() + " " + user.getFirstName() + " " + user.getMiddleName();
    }

    public static void checkUpdatePossibility(User userCreator, Authentication authentication) {
        Set<Role> userRoles = userCreator.getUserRoles();

        boolean isAdministratorRole = userRoles.stream().anyMatch(n -> (n.getName().equals(ADMIN_ROLE)));

        if (!isAdministratorRole && !authentication.getName().equals(userCreator.getLogin())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Нет доступа!");
        }
    }

    public static boolean isAdmin(User user) {
        return user.getUserRoles().stream().map(Role::getName).toList().contains(ADMIN_ROLE);
    }

    public static String getMd5NameFromDocumentName(String documentName) {
        String cleanDocumentName = StringUtils.cleanPath(documentName);
        String documentNameWithoutExtension = FilenameUtils.removeExtension(cleanDocumentName);
        String documentNameWithoutExtensionWithCurrentTime = documentNameWithoutExtension + Instant.now().toString();
        String documentNameExtension = FilenameUtils.getExtension(cleanDocumentName);
        String md5Name = DigestUtils.md5DigestAsHex(documentNameWithoutExtensionWithCurrentTime.getBytes(StandardCharsets.UTF_8));
        return md5Name + "." + documentNameExtension;
    }
}
