package ru.skypro.homework.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@Entity
@Table(name = "advertisement")
/*
проработать связь сущностей автор-объявление
 */
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ads_id",  nullable = false)
    private int pk;                 //'id объявления'

    private String imageAd;         //'ссылка на картинку объявления'
    private int price;              // 'цена объявления'
    private String title;           // 'заголовок объявления'
    private String adsDescription;  // 'описание объявления'

    private int authorId;              // id автора объявления

//    @ManyToOne
//    @JoinColumn(name ="user_id")/
//    private User user;           //' автор объявления'
}
