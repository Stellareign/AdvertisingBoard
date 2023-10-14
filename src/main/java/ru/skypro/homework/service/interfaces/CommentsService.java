package ru.skypro.homework.service.interfaces;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;

import java.util.Map;

@Service
public interface CommentsService {

    Map<Integer, Comment> getAllComments();

    Comment getComment(int pk);

    Comment addComment(String text, Ad adId);

    boolean deleteComment(int pk);


    Comment updateComment(int pk, CreateOrUpdateCommentDTO updateComment);


//    User getUserId(int id);
//
//    User getUserImage (String authorImage);
//
//    Comment getCreatedMoment (int createdAt);
//
//    Comment getPk (int pk);
//
//    User getUserFirstName (String firstName );
//
//    Comment getText (String text)



}
