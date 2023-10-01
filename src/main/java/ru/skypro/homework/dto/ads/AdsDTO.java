package ru.skypro.homework.dto.ads;

import lombok.*;
import ru.skypro.homework.entity.Ad;

import java.util.List;

/*
За Алексеем
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsDTO {
    private int pk;
    private List<Ad> adList;
}