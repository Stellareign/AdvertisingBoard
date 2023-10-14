package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exceptions.RecordNotFoundException;
import ru.skypro.homework.repository.CommentsRepository;
import ru.skypro.homework.service.interfaces.CommentsService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;

    public CommentsServiceImpl(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    private final Map<Integer, Comment> commentsMap = new HashMap<>();

//    @Override
//    public List <Comment> result(Ad adsId){
//        return
//      commentsRepository.findAll()
//                .stream()
//                .filter(e -> e.getAdId().equals( adsId))
//                .collect(Collectors.toList());
//    }

    @Override
    public Map<Integer, Comment> getAllComments() {
        return commentsMap;
    }

    @Override
    public Comment getComment(int pk) {
        return commentsRepository.findById(pk).orElseThrow();
    }

    @Override
    public Comment addComment(String text, Ad adId) {
        Comment comment = new Comment(text, adId);
        return commentsRepository.save(comment);
    }

    @Override
    public boolean deleteComment(int pk) {
        Optional<Comment> optionalComments = commentsRepository.findById(pk);
        if (optionalComments.isPresent()) {
            commentsRepository.deleteById(pk);
            return true;
        } else {
            new RecordNotFoundException(String.valueOf(pk));
            return false;
        }

    }

    @Override
    public Comment updateComment(int pk, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Optional<Comment> commentsOptional = commentsRepository.findById(pk);
        if (commentsOptional.isEmpty()){
            new RecordNotFoundException(String.valueOf(pk));
        }
        Comment existingComm = commentsOptional.get();
        existingComm.setText(createOrUpdateCommentDTO.getText());
        return commentsRepository.save(existingComm);
    }
}
