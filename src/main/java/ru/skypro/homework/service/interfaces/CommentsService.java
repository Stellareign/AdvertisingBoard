package ru.skypro.homework.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.CommentDTO;
import ru.skypro.homework.dto.comments.CommentsDTO;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.User;


@Service
public interface CommentsService {

    CommentsDTO getAllComments(Integer adId);

    void deleteComment(int adId, int pk);

    boolean checkAccessToComments(int id, String username);

    CommentDTO updateComment(Integer adId, Integer pk, CreateOrUpdateCommentDTO updateComment);

    CommentDTO addComment(CreateOrUpdateCommentDTO createOrUpdateComment, Integer adId, Authentication authentication);

    User getAuthorByCommentId(Integer pk);


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
