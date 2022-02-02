package ru.iteco.fmh.service.news;

import ru.iteco.fmh.dto.news.NewsDto;

import java.security.Principal;
import java.util.List;

/**
 * сервис для работы с новостями
 */
public interface NewsService {
    /**
     * возвращает список всех новостей
     */
    List<NewsDto> getAllNews(Principal principal);

    /**
     * возвращает новость для просмотра
     *
     * @param id ид новости
     * @return новость с полной информацией
     */
    NewsDto getNews(int id);

    /**
     * создает новую новость/обновляет новость
     *
     * @param newsDto информация по новости для обновления
     * @return сущность
     */
    NewsDto createOrUpdateNews(NewsDto newsDto);

    /**
     * удаление новости
     *
     * @param id ид новости
     */
    void deleteNews(int id);
}
