package ru.skypro.homework.dto.ads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.skypro.homework.dto.user.AddUserDTO;

/*
За Алексеем
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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