package ru.iteco.fmh.converter.news.toModel;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
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
    public News convert(@NonNull NewsDto newsDto) {
        News news = new News();
        BeanUtils.copyProperties(newsDto, news);

        User creator = userDtoToUserConverter.convert(newsDto.getCreator());
        NewsCategory newsCategory = newsCategoryDtoToNewCategoryConverter.convert(newsDto.getNewsCategory());

        news.setCreator(creator);
        news.setNewsCategory(newsCategory);
        return news;
    }
}
