package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
/*
комменты за Тимуром
проработать связь сущностей автор-комментарий и/или комментарий-объявление
 */
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id",  nullable = false)
    private int pk;    // id комментания

    @OneToOne
    @JoinColumn (name = "user_id",  nullable = false)
    private User authorId = getAuthorId();  // id автора комментария

    @ManyToOne
    @JoinColumn (name = "first_name",  nullable = false)
    private User authorFirstName = getAuthorFirstName();

    @ManyToOne
    @JoinColumn (name = "avatar",  nullable = false)
    private User authorImage = getAuthorImage();

    @Column(name = "date_time",  nullable = false)
    private LocalDateTime createdAt; // дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "ads_id",  nullable = false)
    private Ad adId;  // id объявления

    @Column (name = "comment_text")
    private String text;

    public Comments(User authorFirstName, String text) {
        this.authorFirstName = authorFirstName;
        this.text = text;
    }

    public Comments(String text, Ad adId) {
        this.text = text;
        this.adId = adId;
    }

}
