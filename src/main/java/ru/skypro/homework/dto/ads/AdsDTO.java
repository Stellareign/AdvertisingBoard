package ru.skypro.homework.dto.ads;

import lombok.*;
import org.springframework.stereotype.Component;

/*
За Алексеем
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AdsDTO {
    int pk;
    String imageAd;
    int price;
    String title;
    String adsDescription;
    private int authorId;
//    @JsonProperty("user")
//    AddUserDTO author;

}