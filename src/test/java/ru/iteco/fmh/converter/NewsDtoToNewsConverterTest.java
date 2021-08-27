package ru.iteco.fmh.converter;

import org.junit.Test;
import ru.iteco.fmh.converter.news.toDto.NewsCategoryToNewsCategoryDtoConverter;
import ru.iteco.fmh.converter.news.toModel.NewsCategoryDtoToNewsCategoryConverter;
import ru.iteco.fmh.converter.news.toModel.NewsDtoToNewsConverter;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.news.News;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.*;

public class NewsDtoToNewsConverterTest {
    UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
    NewsCategoryDtoToNewsCategoryConverter newsCategoryDtoToNewCategoryConverter = new NewsCategoryDtoToNewsCategoryConverter();

    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();
    NewsCategoryToNewsCategoryDtoConverter newsCategoryToNewsCategoryDtoConverter = new NewsCategoryToNewsCategoryDtoConverter();

    NewsDtoToNewsConverter sut = new NewsDtoToNewsConverter(userDtoToUserConverter, newsCategoryDtoToNewCategoryConverter);

    @Test
    public void convertShouldPassSuccess() {
        // given
        NewsDto newsDto = getNewsDto();

        News result = sut.convert(newsDto);

        assertAll(
                ()->assertEquals(newsDto.getId(), result.getId()),
                ()->assertEquals(newsDto.getCreateDate(), result.getCreateDate()),
                ()->assertEquals(newsDto.getCreator(), userToUserDtoConverter.convert(result.getCreator())),
                ()->assertEquals(newsDto.getDescription(), result.getDescription()),
                ()->assertEquals(newsDto.getNewsCategory(), newsCategoryToNewsCategoryDtoConverter.convert(result.getNewsCategory())),
                ()->assertEquals(newsDto.getTitle(), result.getTitle()),
                ()->assertEquals(newsDto.getPublishDate(), result.getPublishDate()),
                ()->assertEquals(newsDto.isPublishEnabled(), result.isPublishEnabled())
        );
    }
}
