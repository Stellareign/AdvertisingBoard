package ru.skypro.homework.service.interfaces;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Comment;

import java.util.List;

@Service
public interface CommentsService {

    List<Comment> allComm(int adsId);
}
