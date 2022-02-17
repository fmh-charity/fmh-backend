package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.news.News;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findAllByPublishDateLessThanEqualAndDeletedIsFalseOrderByPublishDateDesc(Instant publishDate);

    List<News> findAllByPublishDateLessThanEqualAndDeletedIsFalseAndPublishEnabledIsTrueOrderByPublishDateDesc(Instant publishDate);

    Optional<News> findById(int newsId);

    Optional<News> findByIdAndPublishEnabledIsTrue(int newsId);

}
