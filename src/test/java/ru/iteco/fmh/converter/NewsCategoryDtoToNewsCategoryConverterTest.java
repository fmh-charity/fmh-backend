package ru.iteco.fmh.converter;

import org.junit.Test;
import ru.iteco.fmh.converter.news.NewsCategoryDtoToNewsCategoryConverter;
import ru.iteco.fmh.dto.news.NewsCategoryDto;
import ru.iteco.fmh.model.news.NewsCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getNewsCategoryDto;

public class NewsCategoryDtoToNewsCategoryConverterTest {
    NewsCategoryDtoToNewsCategoryConverter sut = new NewsCategoryDtoToNewsCategoryConverter();

    @Test
    public void convertShouldPassSuccess() {
        // given
        NewsCategoryDto newsCategoryDto = getNewsCategoryDto();

        NewsCategory result = sut.convert(newsCategoryDto);

        assertEquals(newsCategoryDto.getId(), result.getId());
        assertEquals(newsCategoryDto.getName(), result.getName());
    }
}
