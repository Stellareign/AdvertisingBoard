package ru.skypro.homework.repository;

import org.hibernate.annotations.Comment;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.skypro.homework.models.Ad;

@EnableJpaRepositories
@Service
public interface AdsRepository extends JpaRepository<Ad, Integer> {
}
