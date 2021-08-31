package ru.iteco.fmh.converter.news;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.UserDtoToUserConverter;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.news.News;
import ru.iteco.fmh.model.news.NewsCategory;
import ru.iteco.fmh.model.user.User;

@Component
@RequiredArgsConstructor
public class NewsDtoToNewsConverter implements Converter<NewsDto, News> {
    private final UserDtoToUserConverter userDtoToUserConverter;
    private final NewsCategoryDtoToNewsCategoryConverter newsCategoryDtoToNewCategoryConverter;

    @Override
    public News convert(@NonNull NewsDto dto) {
        News news = new News();
        BeanUtils.copyProperties(dto, news);

        User creator = dto.getCreator() != null
                ? userDtoToUserConverter.convert(dto.getCreator()) : null;
        NewsCategory newsCategory = dto.getNewsCategory() != null
                ? newsCategoryDtoToNewCategoryConverter.convert(dto.getNewsCategory()) : null;

        news.setCreator(creator);
        news.setNewsCategory(newsCategory);
        return news;
    }
}
