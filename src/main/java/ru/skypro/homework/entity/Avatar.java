package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
@Table(name = "avatars")
public class Avatar {
//    @Id
//    @Column(name = "image_id", nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
    @Id
    @Column(name = "image_id", nullable = false)
    private String id;

//    @Lob
    @Column(name = "image_data", columnDefinition = "bytea")
//    @Type(type = "org.hibernate.type.ImageType")
    private byte[] imageData;

    @Column(name = "image_path")
    private String imagePath;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return Objects.equals(id, avatar.id) && Arrays.equals(imageData, avatar.imageData) && Objects.equals(imagePath,
                avatar.imagePath);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, imagePath);
        result = 31 * result + Arrays.hashCode(imageData);
        return result;
    }
}
