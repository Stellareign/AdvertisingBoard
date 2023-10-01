package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Comments;

import java.util.List;

@Service
public interface CommentsService {

    List<Comments> allComm(int adsId);
}
