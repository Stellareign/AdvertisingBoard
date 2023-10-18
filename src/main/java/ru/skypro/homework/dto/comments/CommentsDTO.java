package ru.skypro.homework.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.Comment;

import java.util.List;
/*
ВСЕ КОММЕНТАРИИ
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component

public class CommentsDTO {

    private Integer count; // общее количество комментариев

    private List<CommentDTO> results; //результат вывода всех найденных комментов
//
//    // ============== СТАТИЧЕСКАЯ ФАБРИКА СБОРКА DTO: ==============
//    public static CommentsDTO from(int count, Comment comments) { // "статическая фабрика - создание DTO из заданных "компонентов"
//        return new CommentsDTO(count, comments.getText(), comments.getAuthorImage(),
//                comments.getAuthorFirstName(), comments.getCreatedAt(), comments.getPk());
//    }

}
