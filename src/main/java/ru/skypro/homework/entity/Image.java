package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Image {
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "image")
    private byte[] imageData;

//    @Column(name = "image_path")
//    private String imagePath;

    @Column(name = "image_size")
    private Long imageSize;
}
