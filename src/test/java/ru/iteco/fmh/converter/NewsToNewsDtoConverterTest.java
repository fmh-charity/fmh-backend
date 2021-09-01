package ru.iteco.fmh.converter;

import org.junit.Test;
import ru.iteco.fmh.converter.news.NewsCategoryToNewsCategoryDtoConverter;
import ru.iteco.fmh.converter.news.NewsToNewsDtoConverter;
import ru.iteco.fmh.converter.user.UserToUserDtoConverter;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.news.News;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getNews;

public class NewsToNewsDtoConverterTest {
    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();
    NewsCategoryToNewsCategoryDtoConverter newsCategoryToNewsCategoryDtoConverter = new NewsCategoryToNewsCategoryDtoConverter();

    NewsToNewsDtoConverter sut = new NewsToNewsDtoConverter(userToUserDtoConverter, newsCategoryToNewsCategoryDtoConverter);

    @Test
    public void convertShouldPassSuccess() {
        // given
        News news = getNews();

        NewsDto result = sut.convert(news);

        assertAll(
                () -> assertEquals(news.getId(), result.getId()),
                () -> assertEquals(news.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(userToUserDtoConverter.convert(news.getCreator()), result.getCreator()),
                () -> assertEquals(news.getDescription(), result.getDescription()),
                () -> assertEquals(newsCategoryToNewsCategoryDtoConverter.convert(news.getNewsCategory()), result.getNewsCategory()),
                () -> assertEquals(news.getTitle(), result.getTitle()),
                () -> assertEquals(news.getPublishDate(), result.getPublishDate()),
                () -> assertEquals(news.isPublishEnabled(), result.isPublishEnabled())
        );
    }
}
