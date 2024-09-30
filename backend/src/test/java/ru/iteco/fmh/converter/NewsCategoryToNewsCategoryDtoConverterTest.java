package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.news.NewsCategoryToNewsCategoryDtoConverter;
import ru.iteco.fmh.dto.news.NewsCategoryDto;
import ru.iteco.fmh.model.news.NewsCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getNewsCategory;

public class NewsCategoryToNewsCategoryDtoConverterTest {
    NewsCategoryToNewsCategoryDtoConverter sut = new NewsCategoryToNewsCategoryDtoConverter();

    @Test
    public void convertShouldPassSuccess() {
        // given
        NewsCategory newsCategory = getNewsCategory();

        NewsCategoryDto result = sut.convert(newsCategory);

        assertEquals(newsCategory.getId(), result.getId());
        assertEquals(newsCategory.getName(), result.getName());
    }
}
