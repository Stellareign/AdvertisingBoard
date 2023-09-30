package ru.skypro.homework.dto.ads;

import lombok.Data;

@Data
public class ExtendedAdDTO {
    private int pk; //id объявления

    private String description;
    private int price;
    private  String title;
    private String image; // фото товара

    private String authorFirstName;
    private String authorLastName;
    private String email;
    private String phone;

}
