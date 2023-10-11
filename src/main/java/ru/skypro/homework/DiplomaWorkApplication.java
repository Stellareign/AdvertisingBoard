package ru.skypro.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiplomaWorkApplication {

  public static void main(String[] args) {

    Logger logger = LoggerFactory.getLogger(DiplomaWorkApplication.class);
    logger.debug(" Start Diploma Work Application");

    SpringApplication.run(DiplomaWorkApplication.class, args);



  }
}
