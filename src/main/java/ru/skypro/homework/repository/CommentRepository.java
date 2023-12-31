package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Comment;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByAds_Pk(int adId);


    void deleteCommentsByAds_Pk(int adId);
    Comment findCommentByPk (int pk);

}
