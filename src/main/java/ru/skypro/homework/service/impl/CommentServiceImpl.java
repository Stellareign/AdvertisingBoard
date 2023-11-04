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

    /**
     * Метод для получения списка всех комментариев по идентификатору объявления.
     *
     * @param adId идентификатор объявления
     * @return объект класса CommentsDTO, содержащий количество комментариев и список объектов класса CommentDTO
     */
    @Override
    public CommentsDTO getAllComments(Integer adId) {

        List<Comment> commentsList = commentRepository.findByAds_Pk(adId);
        List<CommentDTO> commentDTO = new ArrayList<>();
        for (Comment comment : commentsList) {
            commentDTO.add(commentMapping.mapToDto(comment));
        }
        return new CommentsDTO(commentDTO.size(), commentDTO);
    }

    /**
     * Метод создания комментария к объявлению.
     * Принимает на вход три параметра:
     * @param createOrUpdateComment - файл DTO
     * @param adId - идентификатор объявления,к которому будет создан коммент
     * @param authentication - определение текущего пользователя - автора комментария
     * @throws RecordNotFoundException если объявление с указанным идентификатором не найдено
     * @return commentDTO
     * @throws RecordNotFoundException если комментарий с указанным id не найден
     */
    @Override
    public CommentDTO addComment(CreateOrUpdateCommentDTO createOrUpdateComment, Integer adId,
                                 Authentication authentication) {
        Optional<AdEntity> ad = adsRepository.findById(adId);
        if (ad.isEmpty()) {
            throw new RecordNotFoundException("Запись не найдена");
        }
        User user = userRepository.findByUsername(authentication.getName());
        Comment comment = commentMapping.mapToEntity(createOrUpdateComment, user, ad.get());
        comment.setCreatedAt(System.currentTimeMillis());
        commentRepository.save(comment);
        CommentDTO commentDTO = commentMapping.mapToDto(comment);
        return commentDTO;
    }

    /**
     * Метод поиска атора комментария по идентификатору комментария.
     * Принимает на вход:
     * @param pk - идентификатор комментария
     *           Ищет автора комментария в БД
     * @see CommentRepository#findCommentByPk(int) по id (pk) коммента
     * @return {@link User}
     */
    @Override
    public User getAuthorByCommentId(Integer pk) {
        if (commentRepository.findById(pk).isPresent()) {
            return commentRepository.findById(pk).get().getAuthor();
        } else {
            throw new RecordNotFoundException("Комментарий не найден!");
        }
    }

    /**
     * Удаление комментария по id
     * @param adId - id объявления
     * @param pk- id комментария
     * @see CommentRepository#findByAds_Pk(int)  - поиск комментария В БД по id объявления
     * @see CommentRepository#deleteCommentsByAds_Pk(int) - удаление комментрария из БД по id комментария
     */
    @Override
    public void deleteComment(int adId, int pk) {
        commentRepository.findByAds_Pk(adId);

        if (commentRepository.findById(pk).isPresent()) {
            commentRepository.deleteById(pk);
        }
    }

    /**
     * Обновление комментария
     * @param adId - id  объявления
     * @param pk - id комментария
     * @param updateComment -тело запроса в контроллере
     * @see CommentRepository#findCommentByPk(int)
     * @see CommentRepository#save(Object)
     * @see CommentMapping#mapToDto(Comment) - commentDTO
     * @return {@link CommentDTO}
     * @throws RecordNotFoundException если комментарий с указанным id не найден
     */
    @Override
    public CommentDTO updateComment(Integer adId, Integer pk, CreateOrUpdateCommentDTO updateComment) {
        Optional<Comment> commentOpt = commentRepository.findById(pk);
        if (commentOpt.isEmpty()) {
            throw new RecordNotFoundException("Comment not found # id = " + pk);
        }
        Comment comment = commentOpt.get();
        comment.setText(updateComment.getText());
        commentRepository.save(comment);
        CommentDTO commentDTO = commentMapping.mapToDto(comment);
        return commentDTO;
    }
}
