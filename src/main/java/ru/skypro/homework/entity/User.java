package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;


import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;


@Entity
@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "authors")

public class User {

    @Id
    @Column(name = "author_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login/e-mail", nullable = false)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Неверный формат email")
    private String username; // логин при регистрации - e-mail

    @Column(name = "first_name", nullable = false)
    @Size(min = 3, max = 10, message = "Введите не менее 3 и не более 10 символов")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 3, max = 10, message = "Введите не менее 3 и не более 10 символов")
    private String lastName;

    @Pattern(regexp = "\\+7\\s?\\(\\d{3}\\)\\s?\\d{3}-\\d{2}-\\d{2}", message = "Неверный формат номера телефона")
    @Column(name = "phone", nullable = false)
    private String phone;

    //**********************************


    @Column(name = "avatar")
    private String image; // ссылка на аватар

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    // **********************************
    @Column(name = "user_password", nullable = false)
    @Size(min = 8, max = 16)
    private String currentPassword;


//*********************** конструкторы **************************

    public User(String username) {
        this.username = username;
    }

// контсруктор при регистрации:
    public User(String username, String firstName, String lastName, Role role, String phone, String currentPassword) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phone = phone;
        this.currentPassword = getCurrentPassword();

    }
    //*************************** преобразование телефонного номера в id ***********
//    private Integer phoneToId (String phone) {
//        String phoneToNum = phone.replaceAll("\\D", ""); // удаление всех нецифровых символов
//        id = Integer.parseInt(phoneToNum); // преобразование строки в числовое значение
//        return id;
//    }

    //******************* геттеры и сеттеры ***********


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }


    //*******************************************************

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName) && Objects.equals(phone, user.phone)
                && Objects.equals(image, user.image) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, phone, image, role);
    }
}
