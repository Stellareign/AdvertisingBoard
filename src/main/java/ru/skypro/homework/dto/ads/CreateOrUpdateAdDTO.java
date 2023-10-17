package ru.skypro.homework.dto.ads;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class CreateOrUpdateAdDTO {
    private String title;
    private int price;
    private String description;
}
