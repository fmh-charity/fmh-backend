package ru.iteco.fmh;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
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

    public Instant getInstantFromString(String date) {
        try {
            Date simpleDate = new SimpleDateFormat("dd.MM.yyyy").parse(date);
            return simpleDate.toInstant();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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


}
