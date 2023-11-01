package ru.skypro.homework;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class DiplomaWorkApplication {

  public static void main(String[] args) {

    Logger logger = LoggerFactory.getLogger(DiplomaWorkApplication.class);
    logger.debug(" \n  ********* Start DiplomaWorkApplication *********");

    SpringApplication.run(DiplomaWorkApplication.class, args);



  }
}
