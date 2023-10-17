package ru.skypro.homework.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer pk;    // id комментария

    @Column(name = "comment_text")
    private String text;

    @Column(name = "date_time_create")
    private Long createdAt; // дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ads_id")
    private Ad adId;  // id объявления

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author; // id автора комментария


}
