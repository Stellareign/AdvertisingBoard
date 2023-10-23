package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Comment;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

//    List<Comment> findByAdId(Integer adId);
    List<Comment> findByAdId_Pk (int adPk);
    boolean deleteByPkAndAdId(Integer pk, Integer adId);
    boolean deleteByPkAndAdId_Pk(Integer pk, Integer adId);
    Comment findByPkAndAdId_Pk(Integer pk, Integer adId);
}
