package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", nullable = false)
    private int pk;    // id комментания


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

    //***************************************************************************


    public Comment(String text, Ad adId) {
        this.text = text;
        this.adId = adId;
    }

    //***************************************************************************
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return pk == comment.pk && createdAt == comment.createdAt && Objects.equals(text, comment.text) && Objects.equals(adId, comment.adId) && Objects.equals(authorId, comment.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, text, createdAt, adId, authorId);
    }
}
