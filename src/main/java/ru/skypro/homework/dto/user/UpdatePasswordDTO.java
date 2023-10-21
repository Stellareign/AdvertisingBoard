package ru.skypro.homework.dto.user;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDTO {
    /*
в сваггере тип stringst - это что?
     */
    private String currentPassword;

    private String newPassword;

    //************************************************************
    public static UpdatePasswordDTO fromUserToUpdatePasswordDTO (String newPassword, User user){
        return new UpdatePasswordDTO (user.getPassword(), newPassword);
    }

}