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

public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ads_id", nullable = false)
    private int pk;                 //'id объявления'

    @Column(name = "image_reference")
    private String imageAd;         //'ссылка на картинку объявления'

    @Column(name = "price")
    private int price;              // 'цена объявления'

    @Column(name = "title")
    private String title;           // 'заголовок объявления'

    @Column(name = "description")
    private String adsDescription;  // 'описание объявления'


    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;           // автор объявления
}
