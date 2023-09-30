package ru.skypro.homework.dto.authorization;

import lombok.*;


@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PasswordDTO {
    /*
в сваггере тип stringst - это что?
     */

    private String currentPassword;

    private String newPassword;
}
