package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ad;

import java.util.List;

@EnableJpaRepositories
@Service
public interface AdsRepository extends JpaRepository<Ad, Integer> {
//    @EntityGraph(attributePaths = {"user"})
    @EntityGraph(attributePaths = {"author"})
    @Query(value = "SELECT p FROM Ad p")
    List<Ad> findAll();
}
