package ru.iteco.fmh;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import ru.iteco.fmh.exceptions.NoRightsException;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

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
        Profile userProfile = user.getProfile();
        return userProfile.getLastName() + " " + userProfile.getFirstName() + " " + userProfile.getMiddleName();
    }

    public static boolean checkUpdatePossibility(User userCreator) {
        User currentLoggedInUser = getCurrentLoggedInUser();

        if (!isAdmin(currentLoggedInUser) && !currentLoggedInUser.equals(userCreator)) {
            throw new NoRightsException("Нет доступа!");
        }
        return true;
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

    public static User getCurrentLoggedInUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
