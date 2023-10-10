package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Ad;


public interface AdsRepository extends JpaRepository<Ad, Integer> {
//    @EntityGraph(attributePaths = {"user"})
//    @EntityGraph(attributePaths = {"author"})
//    @Query(value = "SELECT p FROM Ad p")
//    List<Ad> findAll();
}
