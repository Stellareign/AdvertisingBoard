package ru.skypro.homework.entity;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;

@Data
@Entity
@Component
@Table(name = "author")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "eMail")
    private String eMail;

    @Column(name = "phone")
    private String phone;

    @Column(name = "avatar")
    private String image; // ссылка на аватар

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    //******************* геттеры и сеттеры ***********
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
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
}
