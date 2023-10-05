package ru.skypro.homework.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;

//
@Data
@AllArgsConstructor
public class CommentDTO {
    private  int count; // общее количество комментариев

    private int author; // id автора комментария

    private String authorImage; // ссылка на аватар автора комментария

    private String authorFirstName;//    имя создателя комментария

    private long createdAt; // дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970

    private int pk;    // id комментания

    // ============== СТАТИЧЕСКАЯ ФАБРИКА СБОРКА DTO: ==============
//    public static CommentsDTO from(int count, Comment comments) { // "статическая фабрика - создание DTO из заданных "компонентов"
//        return new CommentsDTO(count, comments.getAuthor(), comments.getAuthorImage(),
//                comments.getAuthorFirstName(), comments.getCreatedAt(), comments.getPk());
//    }
}
