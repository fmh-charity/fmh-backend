package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.news.News;

@Repository
public interface AdvertisementRepository extends JpaRepository<News, Integer> {
}
