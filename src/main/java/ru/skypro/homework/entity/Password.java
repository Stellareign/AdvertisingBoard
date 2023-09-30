package ru.skypro.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_id")
    private int id;

    @Column(name = "password")
    @Size(min = 8, max = 16)
    private String password;

    @OneToOne
    @JoinColumn(name ="user_id")
    private User user;

}
