package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.models.Comments;

import java.util.List;
import java.util.Map;
@Service
public interface CommentsService {

    List<Comments> allComm(int adsId);
}
