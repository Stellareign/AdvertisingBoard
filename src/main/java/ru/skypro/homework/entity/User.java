package ru.skypro.homework.entity;

import lombok.*;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
/*
Юзер за Настей
 */

@Data
@Entity
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
    private String image;

    @Column(name = "role")
    private Role role;

}
