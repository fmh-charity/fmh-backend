package ru.iteco.fmh.converter.news;

import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.model.news.News;

@Component
//@RequiredArgsConstructor
public class NewsToNewsDtoConverter implements Converter<News, NewsDto> {
    @Override
    public NewsDto convert(@NonNull News news) {
        NewsDto newsDto = new NewsDto();
        BeanUtils.copyProperties(news, newsDto);
        newsDto.setNewsCategoryId(news.getNewsCategory().getId());
        newsDto.setCreatorId(news.getCreator().getId());
        return newsDto;
    }
}
