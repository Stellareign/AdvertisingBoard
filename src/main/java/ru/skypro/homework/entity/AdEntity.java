package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ads")
@JsonIgnoreProperties({"hibernateLazyInitializer"})

public class AdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(generator = "sequence")
//    @Column(name = "id", nullable = false)
    private int pk;                         //'id объявления'
    private int price;                      // 'цена объявления'
    private String title;                   // 'заголовок объявления'
    private String description;             // 'описание объявления'
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;          // автор объявления
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "image_id")
    private AdsImage image;                   //'ссылка на картинку объявления'

    public AdEntity(String title, int price, String description, AdsImage image, User author) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.author = author;
    }
    public AdEntity(String title, int price, String description, User author) {
        this.title = title;
        this.price = price;
        this.description = description;
this.author = author;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "pk=" + pk +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author_id=" + author.getId() +
                '}';
    }
}
