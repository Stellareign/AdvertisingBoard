package ru.skypro.homework.service.interfaces;

import ru.skypro.homework.dto.comments.CommentDTO;
import ru.skypro.homework.dto.comments.CommentsDTO;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;


public interface CommentsService {

   CommentsDTO getAllComments(Integer adId);



    boolean deleteComment(Integer adId, Integer pk);


    CommentDTO updateComment(Integer adId, Integer pk, CreateOrUpdateCommentDTO updateComment);

    CommentDTO addComment(CreateOrUpdateCommentDTO createOrUpdateComment, Integer adId, String userInfo);


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
