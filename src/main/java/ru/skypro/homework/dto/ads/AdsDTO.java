package ru.skypro.homework.dto.ads;

import lombok.*;
import ru.skypro.homework.entity.AdEntity;

import java.util.List;

/*
За Алексеем
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsDTO {
    private int count;
    private List<Ad> results;
}