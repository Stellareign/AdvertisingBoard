package ru.skypro.homework.service.MapperUtil;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.CommentDTO;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import java.util.Objects;

@Service
public class CommentMapping {

    /**
     * Метод, который преобразует объект класса Comment в объект класса CommentDTO.
     * @param entity объект класса Comment, который необходимо преобразовать.
     * @return объект класса CommentDTO с данными из объекта entity.
     */
    public CommentDTO mapToDto(Comment entity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPk(entity.getPk());
        commentDTO.setAuthor(entity.getAuthor().getId());
        commentDTO.setAuthorFirstName(entity.getAuthor().getFirstName());
        commentDTO.setAuthorImage("/users/image/" + entity.getAuthor().getId());
        commentDTO.setCreatedAt(Objects.requireNonNullElse(entity.getCreatedAt(), 0L));
        commentDTO.setText(entity.getText());

        return commentDTO;
    }

    /**
     * Метод, который преобразует объект класса CreateOrUpdateCommentDTO в объект класса Comment.
     * @param createOrUpdateComment объект класса CreateOrUpdateCommentDTO, содержащий информацию о комментарии.
     * @param author объект класса User, являющийся автором комментария.
     * @param ad объект класса AdEntity, к которому относится комментарий.
     * @return объект класса Comment, содержащий информацию о комментарии.
     */
    public Comment mapToEntity(CreateOrUpdateCommentDTO createOrUpdateComment, User author, AdEntity ad) {
        Comment entity = new Comment();
        entity.setText(createOrUpdateComment.getText());
        entity.setAuthor(author);
        entity.setAds(ad);
        entity.setCreatedAt(System.currentTimeMillis());
        return  entity;
    }
//    public Comment toComment(CommentEntity commentEntity) {
//        Comment comment = new Comment();
//        comment.setAuthor(commentEntity.getUserEntity().getId());
//        comment.setAuthorImage("/users/image/" + commentEntity.getUserEntity().getId());
//        comment.setAuthorFirstName(commentEntity.getUserEntity().getFirstName());
//        comment.setCreatedAt(commentEntity.getCreatedAt());
//        comment.setPk(commentEntity.getId());
//        comment.setText(commentEntity.getText());
//        return comment;
//    }
}
