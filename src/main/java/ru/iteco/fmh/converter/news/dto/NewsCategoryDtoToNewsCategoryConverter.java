package ru.iteco.fmh.converter.news.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.news.NewsCategoryDto;
import ru.iteco.fmh.model.news.NewsCategory;

@Component
public class NewsCategoryDtoToNewsCategoryConverter implements Converter<NewsCategoryDto, NewsCategory> {
    @Override
    public NewsCategory convert(NewsCategoryDto newsCategoryDto) {
        NewsCategory newsCategory = new NewsCategory();
        BeanUtils.copyProperties(newsCategoryDto, newsCategory);
        return newsCategory;
    }
}
