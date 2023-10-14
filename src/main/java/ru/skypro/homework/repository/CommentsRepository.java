package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Comment;


public interface CommentsRepository extends JpaRepository<Comment, Integer> {
}
