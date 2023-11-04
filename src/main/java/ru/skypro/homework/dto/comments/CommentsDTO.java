package ru.skypro.homework.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
/*
ВСЕ КОММЕНТАРИИ
 */


@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommentsDTO {

    private Integer count; // общее количество комментариев

    private List<CommentDTO> results; //результат вывода всех найденных комментов

}
