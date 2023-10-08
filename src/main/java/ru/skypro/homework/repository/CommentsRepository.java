package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Comments;


public interface CommentsRepository extends JpaRepository<Comments, Integer> {
}
