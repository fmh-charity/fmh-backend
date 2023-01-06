package ru.iteco.fmh;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Util {
    private final UserRepository userRepository;

    String administrator = "ROLE_ADMINISTRATOR";

    public String getCreatorName(Integer id) {
        User userById = userRepository.findUserById(id);
        return userById.getLastName()
                + " "
                + userById.getFirstName()
                + " "
                + userById.getMiddleName();
    }

    public Instant getInstantFromLocalDateAtStartOfDay(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    public Instant getInstantFromLocalDateToEndOfDay(LocalDate localDate) {
        return localDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();
    }

    public String getCreatorNameFromNews(NewsDto newsDto) {
        User creator = userRepository.findUserById(newsDto.getCreatorId());
        return creator.getLastName()
                + " "
                + creator.getFirstName()
                + " "
                + creator.getMiddleName();
    }

    public void checkUpdatePossibility(User userCreator, Authentication authentication) {
        List<Role> userRoles = userCreator.getUserRoles();

        boolean isAdministratorRole = userRoles.stream().anyMatch(n -> (n.getName().equals(administrator)));

        if (!isAdministratorRole && !authentication.getName().equals(userCreator.getLogin())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Нет доступа!");
        }
    }

    public boolean isAdmin(User user) {
        return userRepository.findUserByLogin(user.getLogin()).getUserRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList())
                .contains("ROLE_ADMINISTRATOR");
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
