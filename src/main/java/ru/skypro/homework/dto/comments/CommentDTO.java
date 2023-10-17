package ru.skypro.homework.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

//
@Data
@AllArgsConstructor
@NoArgsConstructor


public class CommentDTO {

    private Integer author; // id автора комментария

    private String authorImage; // ссылка на аватар автора комментария

    private String authorFirstName;//    имя создателя комментария

    private Long createdAt; // дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970

    private Integer pk;    // id комментания

    private String text;


}
