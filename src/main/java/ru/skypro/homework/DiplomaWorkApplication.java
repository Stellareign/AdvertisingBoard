package ru.skypro.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;

@SpringBootApplication
public class DiplomaWorkApplication {

  public static void main(String[] args) {

    SpringApplication.run(DiplomaWorkApplication.class, args);

  }
}
