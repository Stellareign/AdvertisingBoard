package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.repository.UserRepository;

import javax.persistence.*;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ads")
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
}
