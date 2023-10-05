package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Comments;

@EnableJpaRepositories
@Service
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
}
