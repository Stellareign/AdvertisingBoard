package ru.skypro.homework.dto.ads;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.AdEntity;

import java.util.List;

/*
За Алексеем
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AdsDTO {
    private int count;
    private List<Ad> results;
}