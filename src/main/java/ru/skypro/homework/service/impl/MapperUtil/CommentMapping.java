package ru.skypro.homework.service.impl.MapperUtil;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comments.CommentDTO;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class CommentMapping {
    public CommentDTO mapToDto(Comment entity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPk(entity.getPk());
        commentDTO.setAuthor(entity.getAuthor().getId());
        commentDTO.setAuthorFirstName(entity.getAuthor().getFirstName());
        commentDTO.setAuthorImage(entity.getAuthor().getImage());
        LocalDateTime time = LocalDateTime.from(entity.getCreatedAt());
        commentDTO.setCreatedAt(time);
        commentDTO.setText(entity.getText());

        return commentDTO;

    }

    public Comment mapToEntity(CreateOrUpdateCommentDTO COUComment, User author, Ad ad) {
        Comment entity = new Comment();
        entity.setText(COUComment.getText());
        entity.setAuthor(author);
        entity.setAdId(ad);
        entity.setCreatedAt(LocalDateTime.from(Instant.now()));

        return  entity;
    }
}
