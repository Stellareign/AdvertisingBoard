package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Table(name = "Images")

public class AdsImage {
    @Id
    @Column(name = "image_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "image")
    private byte[] imageData;

    @OneToOne(optional = true)
    Ad ad;

    //******************************************************************


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdsImage adsImage = (AdsImage) o;
        return Objects.equals(id, adsImage.id) && Arrays.equals(imageData, adsImage.imageData) && Objects.equals(ad, adsImage.ad);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, ad);
        result = 31 * result + Arrays.hashCode(imageData);
        return result;
    }
}
