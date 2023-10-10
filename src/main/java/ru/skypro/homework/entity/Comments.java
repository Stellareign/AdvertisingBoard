package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", nullable = false)
    private int pk;    // id комментания

    @Column(name = "author_first_name", nullable = false)
    private String authorFirstName;

    @Column(name = "author_avatar", nullable = false)
    private String authorImage; // ссылка н аватар автора

    @Column(name = "comment_text")
    private String text;

    @Column(name = "date_time", nullable = false)
    private long createdAt; // дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ads_id", nullable = false)
    private Ad adId;  // id объявления

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User authorId; // id автора комментария


    public Comments(String authorFirstName, String text) {
        this.authorFirstName = authorFirstName;
        this.text = text;
    }

    public Comments(String text, Ad adId) {
        this.text = text;
        this.adId = adId;
    }
}
