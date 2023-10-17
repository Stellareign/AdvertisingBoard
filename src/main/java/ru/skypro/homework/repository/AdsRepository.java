package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.skypro.homework.entity.AdEntity;

@EnableJpaRepositories
public interface AdsRepository extends JpaRepository<AdEntity, Integer> {
}
