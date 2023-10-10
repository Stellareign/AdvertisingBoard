package ru.skypro.homework.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.entity.Comments;

import java.util.List;
import java.util.Map;
/*
ВСЕ КОММЕНТАРИИ
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {

    private int count; // общее количество комментариев

    private Map<Integer, Comments> results; //результат вывода всех найденных комментов

}
