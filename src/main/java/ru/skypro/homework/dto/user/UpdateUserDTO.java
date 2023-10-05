package ru.skypro.homework.dto.user;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Component
public class UpdateUserDTO {

    @Size(min = 3, max = 10)
    private String firstName;

    @Size(min = 3, max = 10)
    private String lastName;

    @Pattern(regexp = "\\+7\\s?\\(\\d{3}\\)\\s?\\d{3}-\\d{2}-\\d{2}")
    private String phone;
}
