package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Comments;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl implements CommentsService {
    private final CommentRepository commentRepository;
    public CommentsServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


@Override
    public List <Comments> allComm(int adsId){
        return
      commentRepository.findAll()
                .stream()
                .filter(e -> e.getAdId() == adsId)
                .collect(Collectors.toList());
    }
}
