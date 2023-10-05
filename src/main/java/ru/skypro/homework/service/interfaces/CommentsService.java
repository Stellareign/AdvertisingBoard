package ru.skypro.homework.service.interfaces;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comments;

import java.util.List;

@Service
public interface CommentsService {

    List<Comments> result(Ad adsId);

    Comments getComment(int pk);

    Comments addComment(String text, Ad adId);

    boolean deleteComment(int pk);


    Comments updateComment(int pk, CreateOrUpdateCommentDTO updateComment);


//    User getUserId(int id);
//
//    User getUserImage (String authorImage);
//
//    Comments getCreatedMoment (int createdAt);
//
//    Comments getPk (int pk);
//
//    User getUserFirstName (String firstName );
//
//    Comments getText (String text)



}
