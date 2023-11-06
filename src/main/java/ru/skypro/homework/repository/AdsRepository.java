package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.skypro.homework.entity.AdEntity;

import java.util.List;


public interface AdsRepository extends JpaRepository<AdEntity, Integer> {
    AdEntity findByPk(int pk);

    AdEntity findByPkAndAuthor_Id(int pk, int author_id);

    AdEntity findAdEntityByAuthor_Username(String username);

    List<AdEntity> findAllByAuthor_Username(String username);

    void deleteAllByAuthor_Username(String username);
}
