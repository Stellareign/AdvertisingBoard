package ru.skypro.homework.dto.ads;

import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateAd {
    private String title;
    private int price;
    private String description;
}
