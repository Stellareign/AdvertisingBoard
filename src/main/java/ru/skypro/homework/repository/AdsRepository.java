package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.skypro.homework.entity.AdEntity;


public interface AdsRepository extends JpaRepository<AdEntity, Integer> {
    AdEntity findByPk(int pk);

}
