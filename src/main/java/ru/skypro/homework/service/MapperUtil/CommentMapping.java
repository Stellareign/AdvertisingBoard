package ru.skypro.homework.service.MapperUtil;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.comments.CommentDTO;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CommentMapping {
    public CommentDTO mapToDto(Comment entity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPk(entity.getPk());
        commentDTO.setAuthor(entity.getAuthor().getId());
        commentDTO.setAuthorFirstName(entity.getAuthor().getFirstName());
        commentDTO.setAuthorImage(entity.getAuthor().getAvatarPath());
        commentDTO.setCreatedAt(Objects.requireNonNullElse(entity.getCreatedAt(), 0L));
        commentDTO.setText(entity.getText());

        return commentDTO;
    }


    public Comment mapToEntity(CreateOrUpdateCommentDTO createOrUpdateComment, User author, AdEntity ad) {
        Comment entity = new Comment();
        entity.setText(createOrUpdateComment.getText());
        entity.setAuthor(author);
        entity.setAds(ad);
        entity.setCreatedAt(System.currentTimeMillis());
        return  entity;
    }

}
