package ru.skypro.homework.dto.ads;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CreateOrUpdateAdDTO {
    private String title;
    private int price;
    private String description;
}
