package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "image")
    private String image;                   //'ссылка на картинку объявления'
    @Column(name = "price", nullable = false)
    private int price;                      // 'цена объявления'
    @Column(name = "title", nullable = false)
    private String title;                   // 'заголовок объявления'
    @Column(name = "description")
    private String description;             // 'описание объявления'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;          // автор объявления

    public Ad(String title, int price, String image, User author) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.author = author;
    }
//    public Ad(String title, int price, String image, int authorId, UserRepository userRepository) {
//        this.userRepository = userRepository;
//        this.title = title;
//        this.price = price;
//        this.image = image;
//        Optional<User> user = userRepository.findById(authorId);
//        user.ifPresent(value -> this.author = value);
//    }
}
