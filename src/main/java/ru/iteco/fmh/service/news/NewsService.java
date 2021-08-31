package ru.iteco.fmh.service.news;

import ru.iteco.fmh.dto.news.NewsDto;

import java.util.List;

/**
 * сервис для работы с новостями
 */
public interface NewsService {
    /**
     * возвращает список всех новостей
     */
    List<NewsDto> getAllNews();

    /**
     * создает новую новость
     *
     * @param newsDto информация по новой новости
     * @return id сохранненой новости
     */
    int createNews(NewsDto newsDto);

    /**
     * возвращает новость для просмотра
     *
     * @param id ид новости
     * @return новость с полной информацией
     */
    NewsDto getNews(int id);

    /**
     * обновляет новость
     *
     * @param newsDto информация по новости для обновления
     * @return обновленная сущность
     */
    NewsDto updateNews(NewsDto newsDto);

    /**
     * удаление новости
     *
     * @param id ид новости
     * @return id удаленной новости
     */
    int deleteNews(int id);
}
