package ru.iteco.fmh;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import java.security.Principal;
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

    public boolean isAdmin(Principal principal) {
        return userRepository.findUserByLogin(principal.getName()).getUserRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList())
                .contains("ROLE_ADMINISTRATOR");
    }


}
