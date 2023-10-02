package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.user.UserDTO;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;


@Entity
@Component
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    private int id;

    @Column(name = "first_name")
    @Size(min = 3, max = 10)
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 3, max = 10)
    private String lastName;

    @Pattern(regexp = "\\+7\\s?\\(\\d{3}\\)\\s?\\d{3}-\\d{2}-\\d{2}")
    @Column(name = "phone", nullable = false)
    private String phone;
    //**********************************
    @Column(name = "e_mail")
    private String email; // логин пользователя

    @Column(name = "avatar")
    private String image; // ссылка на аватар

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    // **********************************
    @Column(name = "user_password")
    @Size(min = 8, max = 16)
    private String currentPassword;
    // ************************************
    @OneToMany
    List<Comment> comment;

    @OneToMany
    List<Ad> ads;

    @OneToOne
    @JoinColumn(name = "image_id")
    Image avatar;

    //*************************** преобразование телефонного номера в id ***********
//    private Integer phoneToId (String phone) {
//        String phoneToNum = phone.replaceAll("\\D", ""); // удаление всех нецифровых символов
//        id = Integer.parseInt(phoneToNum); // преобразование строки в числовое значение
//        return id;
//    }

    //******************* геттеры и сеттеры ***********

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(phone, user.phone) && Objects.equals(image, user.image) && Objects.equals(email, user.email) && role == user.role && Objects.equals(currentPassword, user.currentPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phone, image, email, role, currentPassword);
    }
}
