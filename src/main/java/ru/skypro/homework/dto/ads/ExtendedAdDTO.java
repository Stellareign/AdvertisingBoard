package ru.skypro.homework.dto.ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ExtendedAdDTO {
    private int pk; //id объявления

    private String description;
    private int price;
    private String title;
    private String image; // ссылка на фото товара

    private String authorFirstName;
    private String authorLastName;
    private String email;
    private String phone;

}
