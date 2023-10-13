package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.AdsImage;

public interface AdsImageRepository extends JpaRepository<AdsImage, Integer> {
}
