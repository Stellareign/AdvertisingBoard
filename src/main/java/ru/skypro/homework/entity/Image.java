package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
@Table(name = "Images")
public class Image {
    @Id
    @Column(name = "image_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "image")
    private byte[] imageData;

//    @Column(name = "image_path")
//    private String imagePath;

    @Column(name = "image_size")
    private Long imageSize;

    @OneToOne
    @JoinColumn(name = "author_id")
    private User user;
}
