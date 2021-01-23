package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {
}
