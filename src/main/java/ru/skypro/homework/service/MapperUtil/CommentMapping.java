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
        String pathToImage = checkPathToAvatar(entity);
        commentDTO.setPk(entity.getPk());
        commentDTO.setAuthor(entity.getAuthor().getId());
        commentDTO.setAuthorFirstName(entity.getAuthor().getFirstName());
        commentDTO.setAuthorImage(pathToImage);
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

    /**
     * Метод для проверки пути к аватару автора комментария.
     * Если путь существует и не пустой, то возвращает путь к аватару,
     * иначе возвращает null.
     *
     * @param comment комментарий, для которого нужно проверить путь к аватару
     * @return путь к аватару, если он существует, иначе null
     */
    private String checkPathToAvatar(Comment comment) {
        if (comment.getAuthor().getAvatarPath() != null && !comment.getAuthor().getAvatarPath().isEmpty()) {
            return "/users/image/" + comment.getAuthor().getId();
        }
        return null;
    }
}
