package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Comment;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByAds_Pk(int adId);

    void deleteCommentsByAds_Pk(int ads_pk);
    Optional<Comment> findCommentByPkAndAds_pk (int pk, int ads_pk);

}
