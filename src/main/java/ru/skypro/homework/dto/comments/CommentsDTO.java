package ru.skypro.homework.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.Comment;

import java.util.Map;
/*
ВСЕ КОММЕНТАРИИ
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {

    private int count; // общее количество комментариев

    private Map<Integer, Comment> results; //результат вывода всех найденных комментов

}
