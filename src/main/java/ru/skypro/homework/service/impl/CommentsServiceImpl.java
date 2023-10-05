package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comments;
import ru.skypro.homework.repository.NewCommentsRepository;
import ru.skypro.homework.service.CommentsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl implements CommentsService {
    private final NewCommentsRepository commentRepository;
    public CommentsServiceImpl(NewCommentsRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


@Override
    public List <Comments> result(Ad adsId){
        return
      commentRepository.findAll()
                .stream()
                .filter(e -> e.getAdId().equals( adsId))
                .collect(Collectors.toList());
    }

    @Override
    public Comments addComment(String text) {
        return null;
    }

    @Override
    public Comments deleteComment(int pk) {
        return null;
    }

    @Override
    public Comments updateComment(int pk) {
        return null;
    }
}
