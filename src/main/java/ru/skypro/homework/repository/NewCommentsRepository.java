package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.skypro.homework.entity.Comments;
//
@EnableJpaRepositories

public interface NewCommentsRepository extends JpaRepository<Comments, Integer> {

}
