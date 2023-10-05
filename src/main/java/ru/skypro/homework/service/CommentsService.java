package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comments;
import ru.skypro.homework.entity.User;

import java.util.List;

@Service
public interface CommentsService {

    List<Comments> result(Ad adsId);

    Comments addComment(String text);

    Comments deleteComment(int pk);

    Comments updateComment(int pk);



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
