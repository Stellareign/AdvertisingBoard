package ru.skypro.homework.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.models.Comments;

import java.util.List;
/*
ВСЕ КОММЕНТАРИИ
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {

    private int count; // общее количество комментариев

    private List<Comments> allComm;

//    private int author; // id автора комментария
//    private String authorImage; // ссылка на аватар автора комментария
//    private String authorFirstName;//    имя создателя комментария
//    private long createdAt; // дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
//    private int pk;    // id комментания (или объявления????)



}
