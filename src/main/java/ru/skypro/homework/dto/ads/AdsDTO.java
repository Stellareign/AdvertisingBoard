package ru.skypro.homework.dto.ads;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsDTO {
    private int count;
    private List<Ad> results;
}