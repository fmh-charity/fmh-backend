package ru.iteco.fmh.converter.news;

import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.news.NewsCategoryDto;
import ru.iteco.fmh.model.news.NewsCategory;

@Component
public class NewsCategoryToNewsCategoryDtoConverter implements Converter<NewsCategory, NewsCategoryDto> {
    @Override
    public NewsCategoryDto convert(@NonNull NewsCategory newsCategory) {
        NewsCategoryDto newsCategoryDto = new NewsCategoryDto();
        BeanUtils.copyProperties(newsCategory, newsCategoryDto);
        return newsCategoryDto;
    }
}
