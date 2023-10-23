package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Comment;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByAdId_Pk(int adId);

    boolean deleteByPkAndAdId_Pk(int pk, int adId);

    Comment findByPkAndAdId_Pk(int pk, int adId);
}
