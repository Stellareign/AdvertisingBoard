package ru.skypro.homework;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication
public class DiplomaWorkApplication {
  public static void main(String[] args) {
    SpringApplication.run(DiplomaWorkApplication.class, args);

  }
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration() // задаём настройки маппера
            .setMatchingStrategy(MatchingStrategies.STANDARD) // стратегия соответствия (??? нужна)
            .setFieldMatchingEnabled(true) // сопоставление соответствия полей
            .setSkipNullEnabled(true) // пропуск пустых полей
            .setFieldAccessLevel(PRIVATE); // приватный уровнь доступа
    return mapper;
  }
}
