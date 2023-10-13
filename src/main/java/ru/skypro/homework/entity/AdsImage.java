package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ads_image")
public class AdsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private int imageId;
//    @Lob
    private byte[] image;
//
//    @OneToOne(optional = true)
//    @JoinColumn(referencedColumnName = "id")
////    @JoinColumn(name = "pk")
//    @ToString.Exclude
//    private Ad ads;

    public AdsImage(byte[] image) {
        this.image = image;
    }
}
