package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comments;
import ru.skypro.homework.exceptions.RecordNotFoundException;
import ru.skypro.homework.repository.CommentsRepository;
import ru.skypro.homework.service.interfaces.CommentsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;

    public CommentsServiceImpl(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    private final Map<Integer, Comments> commentsMap = new HashMap<>();

//    @Override
//    public List <Comments> result(Ad adsId){
//        return
//      commentsRepository.findAll()
//                .stream()
//                .filter(e -> e.getAdId().equals( adsId))
//                .collect(Collectors.toList());
//    }

    @Override
    public Map<Integer, Comments> getAllComments() {
        return commentsMap;
    }

    @Override
    public Comments getComment(int pk) {
        return commentsRepository.findById(pk).orElseThrow();
    }

    @Override
    public Comments addComment(String text, Ad adId) {
        Comments comments = new Comments(text, adId);
        return commentsRepository.save(comments);
    }

    @Override
    public boolean deleteComment(int pk) {
        Optional<Comments> optionalComments = commentsRepository.findById(pk);
        if (optionalComments.isPresent()) {
            commentsRepository.deleteById(pk);
            return true;
        } else {
            new RecordNotFoundException(String.valueOf(pk));
            return false;
        }

    }

    @Override
    public Comments updateComment(int pk, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Optional<Comments> commentsOptional = commentsRepository.findById(pk);
        if (commentsOptional.isEmpty()){
            new RecordNotFoundException(String.valueOf(pk));
        }
        Comments existingComm = commentsOptional.get();
        existingComm.setText(createOrUpdateCommentDTO.getText());
        return commentsRepository.save(existingComm);
    }
}
