package ru.iteco.fmh.converter.news.toDto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.dto.news.NewsCategoryDto;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.news.News;

@Component
@RequiredArgsConstructor
public class NewsToNewsDtoConverter implements Converter<News, NewsDto> {
    private final UserToUserDtoConverter userToUserDtoConverter;
    private final NewsCategoryToNewsCategoryDtoConverter newsCategoryToNewsCategoryDtoConverter;

    @Override
    public NewsDto convert(@NonNull News news) {
        NewsDto newsDto = new NewsDto();
        BeanUtils.copyProperties(news, newsDto);

        UserDto creator = news.getCreator()!=null? userToUserDtoConverter.convert(news.getCreator()) : null;
        NewsCategoryDto newsCategory = news.getNewsCategory()!=null?
                newsCategoryToNewsCategoryDtoConverter.convert(news.getNewsCategory()) : null;

        newsDto.setCreator(creator);
        newsDto.setNewsCategory(newsCategory);

        return newsDto;
    }
}
