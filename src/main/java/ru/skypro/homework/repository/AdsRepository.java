package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.entity.AdEntity;

import java.util.List;

@EnableJpaRepositories
public interface AdsRepository extends JpaRepository<AdEntity, Integer> {
    AdEntity findByPk(int pk);

}
