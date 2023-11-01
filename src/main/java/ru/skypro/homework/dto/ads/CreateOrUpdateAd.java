package ru.skypro.homework.dto.ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data

public class CreateOrUpdateAd {
    private String title;
    private int price;
    private String description;
}
