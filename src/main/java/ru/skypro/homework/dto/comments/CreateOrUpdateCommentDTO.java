package ru.skypro.homework.dto.comments;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrUpdateCommentDTO {
   private String text;
}
