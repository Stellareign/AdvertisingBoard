package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ad")
@JsonIgnoreProperties({"hibernateLazyInitializer"})

/*
проработать связь сущностей автор-объявление
 */
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(generator = "sequence")
//    @Column(name = "id", nullable = false)
    private int pk;                         //'id объявления'

//    @Column(name = "image")
    private String image;                   //'ссылка на картинку объявления'

//    @Column(name = "price", nullable = false)
    private int price;                      // 'цена объявления'

//    @Column(name = "title", nullable = false)
    private String title;                   // 'заголовок объявления'

//    @Column(name = "description")
    private String description;             // 'описание объявления'

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
//    @Column(name = "user_id")
    private User author;          // автор объявления

    @ManyToOne
    @JoinColumn (name= "image_ig")
    AdsImage adsImage;


    public Ad(String title, int price, String description, String image, User author) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.author = author;
    }
    public Ad(String title, int price, String description, User author) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return pk == ad.pk && price == ad.price && Objects.equals(image, ad.image) && Objects.equals(title, ad.title) && Objects.equals(description, ad.description) && Objects.equals(author, ad.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, image, price, title, description, author);
    }
}
