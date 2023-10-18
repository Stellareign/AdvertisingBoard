package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.CommentDTO;
import ru.skypro.homework.dto.comments.CommentsDTO;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;

import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import ru.skypro.homework.exceptions.RecordNotFoundException;
import ru.skypro.homework.repository.AdsRepository;

import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.MapperUtil.CommentMapping;
import ru.skypro.homework.service.interfaces.CommentsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentsService {

    private final CommentRepository commentRepository;
    private final CommentMapping commentMapping;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;


    @Override
    public CommentsDTO getAllComments(Integer adId) {

        List<Comment> commentsList = commentRepository.findByAdId(adId);
        List<CommentDTO> commentDTO = new ArrayList<>();

        for (Comment comment : commentsList) {
            commentDTO.add(commentMapping.mapToDto(comment));
        }
        return new CommentsDTO(commentDTO.size(), commentDTO);


    }

    @Override
    public CommentDTO addComment(CreateOrUpdateCommentDTO createOrUpdateComment, Integer adId,
                                 Authentication authentication) {
        Optional<AdEntity> ad = adsRepository.findById(adId);
        if(ad.isEmpty()){ throw new RecordNotFoundException("Запись не найдена");}
        User user = userRepository.findByUsername(authentication.getName());
        Comment comment = commentMapping.mapToEntity(createOrUpdateComment, user, ad.get());
        comment.setCreatedAt(System.currentTimeMillis());
        commentRepository.save(comment);
        return commentMapping.mapToDto(comment);
    }

    @Override
    public boolean deleteComment(Integer adId, Integer pk) {
        if (
        commentRepository.deleteByPkAndAdId(adId, pk)){
            return true;
        } else {
            return false;
        }

    }

    @Override
    public CommentDTO updateComment(Integer adId, Integer pk, CreateOrUpdateCommentDTO updateComment) {
        Comment comment = commentRepository.findByPkAndAdId(pk, adId);
        comment.setText(updateComment.getText());
        commentRepository.save(comment);

        return commentMapping.mapToDto(comment);
    }
}
