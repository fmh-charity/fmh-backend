package ru.iteco.fmh.converter;

import org.junit.Test;
import ru.iteco.fmh.converter.news.NewsToNewsDtoConverter;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.news.News;
import ru.iteco.fmh.model.user.User;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.getNews;
import static ru.iteco.fmh.TestUtils.getUser;

public class NewsToNewsDtoConverterTest {
    UserRepository userRepository = mock(UserRepository.class);
    NewsToNewsDtoConverter sut = new NewsToNewsDtoConverter(userRepository);

    @Test
    public void convertShouldPassSuccess() {
        // given
        News news = getNews();

        User user = getUser();
        user.setId(1);
        when(userRepository.findUserById(any())).thenReturn(user);
        NewsDto result = sut.convert(news);
        assertAll(
                () -> assertEquals(news.getId(), result.getId()),
                () -> assertEquals(news.getCreateDate().toEpochMilli(), result.getCreateDate()),
                () -> assertEquals(news.getCreator().getId(), result.getCreatorId()),
                () -> assertEquals(news.getDescription(), result.getDescription()),
                () -> assertEquals(news.getNewsCategory().getId(), result.getNewsCategoryId()),
                () -> assertEquals(news.getTitle(), result.getTitle()),
                () -> assertEquals(news.getPublishDate().toEpochMilli(), result.getPublishDate()),
                () -> assertEquals(news.isPublishEnabled(), result.isPublishEnabled()),
                () -> assertNotNull(result.getCreatorName())
        );
    }
}
