package ru.skypro.homework.dto.user;

import lombok.*;
import org.springframework.stereotype.Component;


@Component
@Data
public class UpdatePasswordDTO {
    /*
в сваггере тип stringst - это что?
     */
    private String currentPassword;

    private String newPassword;


}