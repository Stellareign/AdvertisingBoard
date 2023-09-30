package ru.skypro.homework.dto.comments;

import lombok.Data;

@Data
public class UpdateCommentsDTO {
    int adId;
    int commentId;
    String text;
}
