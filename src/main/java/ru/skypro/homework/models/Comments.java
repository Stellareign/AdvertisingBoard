package ru.skypro.homework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
/*
проработать связь сущностей автор-комментарий и/или комментарий-объявление
 */
@Component
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id",  nullable = false)
    private int pk;    // id комментания

    @Column(name = "author_id",  nullable = false)
    private int author; // id автора комментария

    @Column(name = "date_time",  nullable = false)
    private long createdAt; // дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970

    @Column(name = "ads_id",  nullable = false)
    private int adId;  // id объявления

}
