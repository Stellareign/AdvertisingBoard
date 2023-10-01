package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ad")
/*
проработать связь сущностей автор-объявление
 */
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int pk;                         //'id объявления'
    private String image;                   //'ссылка на картинку объявления'
    private int price;                      // 'цена объявления'
    private String title;                   // 'заголовок объявления'
    private String description;             // 'описание объявления'
    private int author;                             // id автора объявления
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
//    private User author;          // автор объявления

    public Ad(String title, int price, String image, int author) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.author = author;
    }
}
