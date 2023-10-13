package ru.skypro.homework.dto.ads;

import lombok.Data;
import ru.skypro.homework.entity.AdsImage;
import ru.skypro.homework.entity.User;

@Data
public class Ad {
    private int pk;                         //'id объявления'
    private int price;                      // 'цена объявления'
    private String title;                   // 'заголовок объявления'
    private User author;          // автор объявления
    private AdsImage image;                   //'ссылка на картинку объявления'
}
