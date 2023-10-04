package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Component
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id",  nullable = false)
    private int pk;    // id комментания

    @Column(name = "date_time",  nullable = false)
    private long createdAt; // дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970


    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ads_id")
    private Ad ad;
}
