package ru.iteco.fmh.converter;

import lombok.RequiredArgsConstructor;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.user.User;

@RequiredArgsConstructor
public class ConverterUtil {
    private final UserRepository userRepository;

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
}
