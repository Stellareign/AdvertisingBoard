package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

@Table(name = "users")

public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Неверный формат email")
    private String username; // логин при регистрации - e-mail

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 16)
    private String password;

    @Column(name = "first_name", nullable = false)
    @Size(min = 3, max = 10, message = "Введите не менее 3 и не более 10 символов")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 3, max = 10, message = "Введите не менее 3 и не более 10 символов")
    private String lastName;

    @Pattern(regexp = "\\+7\\s?\\(\\d{3}\\)\\s?\\d{3}-\\d{2}-\\d{2}", message = "Неверный формат номера телефона")
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    //**********************************

//    @OneToOne
//    @JoinColumn(name = "image_id")
//    @Column(columnDefinition = "VARCHAR(255)")
//    private Avatar userAvatar;

    @Column(name = "avatar_path")
    String avatarPath;  //


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    // ******************************************
    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;


//*********************** конструкторы **************************

    public User(String username) {
        this.username = username;
    }

    // конструктор при регистрации:
    public User(String username, String firstName, String lastName, Role role, String phone, String password,
                LocalDate registerDate) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phone = phone;
        this.password = getPassword();
        this.registerDate = registerDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username)
                && Objects.equals(phone, user.phone)
                && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, phone, role);
    }
}
