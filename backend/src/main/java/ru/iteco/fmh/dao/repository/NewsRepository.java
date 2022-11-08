package ru.iteco.fmh.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.news.News;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    Page<News> findAllByDeletedIsFalse(Pageable pageableList);

    Page<News> findAllByPublishDateLessThanEqualAndDeletedIsFalseAndPublishEnabledIsTrue(
            Instant publishDate, Pageable pageableList);

    Optional<News> findByIdAndPublishEnabledIsTrue(int newsId);

}
