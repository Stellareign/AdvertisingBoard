package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ad;

@EnableJpaRepositories
@Service
public interface AdsRepository extends JpaRepository<Ad, Integer> {
}
