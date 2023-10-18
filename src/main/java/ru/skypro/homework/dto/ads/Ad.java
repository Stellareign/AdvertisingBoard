package ru.skypro.homework.dto.ads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ad {
    private int pk;                         //'id объявления'
    private int price;                      // 'цена объявления'
    private String title;                   // 'заголовок объявления'
    private int authorId;          // автор объявления
    private String image;                   //'ссылка на картинку объявления'
}
