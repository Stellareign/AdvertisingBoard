package ru.skypro.homework.service.impl;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import ru.skypro.homework.models.Comments;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentsService;

import java.lang.module.ResolutionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
