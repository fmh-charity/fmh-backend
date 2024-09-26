package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.news.NewsCategory;

@Repository
public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Integer> {
    NewsCategory findNewsCategoryById(Integer id);
}
