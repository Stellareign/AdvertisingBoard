package ru.skypro.homework.dto.ads;

import lombok.Data;

@Data

public class CreateOrUpdateAd {
    private String title;
    private int price;
    private String description;
}
