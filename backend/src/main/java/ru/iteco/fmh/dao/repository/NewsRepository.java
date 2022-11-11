package ru.iteco.fmh.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.news.News;
import ru.iteco.fmh.model.news.NewsCategory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    @Query(value = "SELECT n FROM News n WHERE n.publishDate <= :currentDate "
            + "and (n.publishDate >= :publishDateFrom or cast(:publishDateFrom as timestamp) is null ) "
            + "and (n.publishDate <= :publishDateTo or cast(:publishDateTo as timestamp) is null ) "
            + "and (n.newsCategory = :newsCategory or :newsCategory is null ) "
            + "and n.publishEnabled is true "
            + "and n.deleted is false")
    Page<News> findAllWithFiltersWherePublishDateLessThanCurrentAndDeletedIsFalseAndPublishEnabledIsTrue(
            @Param("newsCategory") NewsCategory newsCategory,
            @Param("currentDate") Instant currentDate,
            @Param("publishDateFrom")Instant publishDateFrom,
            @Param("publishDateTo")Instant publishDateTo, Pageable pageableList
    );

    @Query(value = "SELECT n FROM News n WHERE "
            + "(n.publishDate >= :publishDateFrom or cast(:publishDateFrom as timestamp) is null ) "
            + "and (n.publishDate <= :publishDateTo or cast(:publishDateTo as timestamp) is null ) "
            + "and (n.newsCategory = :newsCategory or :newsCategory is null ) "
            + "and n.deleted is false")
    Page<News> findAllWithFiltersWhereDeletedIsFalse(
            @Param("newsCategory") NewsCategory newsCategory,
            @Param("publishDateFrom")Instant publishDateFrom,
            @Param("publishDateTo")Instant publishDateTo,
            Pageable pageableList
    );

    Optional<News> findByIdAndPublishEnabledIsTrue(int newsId);

}
