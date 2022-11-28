package ru.iteco.fmh.converter;

import org.junit.Test;
import ru.iteco.fmh.converter.news.NewsDtoToNewsConverter;
import ru.iteco.fmh.dao.repository.NewsCategoryRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.news.News;
import ru.iteco.fmh.model.news.NewsCategory;
import ru.iteco.fmh.model.user.User;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.*;

public class NewsDtoToNewsConverterTest {
    UserRepository userRepository = mock(UserRepository.class);
    NewsCategoryRepository newsCategoryRepository = mock(NewsCategoryRepository.class);

    NewsDtoToNewsConverter sut = new NewsDtoToNewsConverter(newsCategoryRepository, userRepository);

    @Test
    public void convertShouldPassSuccess() {
        // given
        NewsDto newsDto = getNewsDtowithDateAndUser();
        newsDto.setNewsCategoryId(1);
        newsDto.setCreatorId(1);
        User user = getUser();
        user.setId(1);
        NewsCategory newsCategory = getNewsCategory();
        newsCategory.setId(1);

        when(userRepository.findUserById(any())).thenReturn(user);
        when(newsCategoryRepository.findNewsCategoryById(any())).thenReturn(newsCategory);

        News result = sut.convert(newsDto);

        assertAll(
                ()->assertEquals(newsDto.getId(), result.getId()),
                ()->assertEquals(newsDto.getCreateDate(), result.getCreateDate().toEpochMilli()),
                ()->assertEquals(newsDto.getCreatorId(), result.getCreator().getId()),
                ()->assertEquals(newsDto.getDescription(), result.getDescription()),
                ()->assertEquals(newsDto.getNewsCategoryId(), result.getNewsCategory().getId()),
                ()->assertEquals(newsDto.getTitle(), result.getTitle()),
                ()->assertEquals(newsDto.getPublishDate(), result.getPublishDate().toEpochMilli()),
                ()->assertEquals(newsDto.isPublishEnabled(), result.isPublishEnabled())
        );
    }
}
