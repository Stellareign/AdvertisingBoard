package ru.skypro.homework.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class MapperUtil {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration() // задаём настройки маппера
                .setMatchingStrategy(MatchingStrategies.STANDARD) // стратегия соответствия (??? нужна)
                .setFieldMatchingEnabled(true) // сопоставление соответствия полей
                .setSkipNullEnabled(true) // пропуск пустых полей
                .setFieldAccessLevel(PRIVATE); // приватный уровень доступа
        return mapper;
    }
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    public  <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(e -> converter.apply(e)).collect(Collectors.toList());
    }
}
