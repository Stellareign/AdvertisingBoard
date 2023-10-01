package ru.skypro.homework.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class MapperUtil {
    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
    public <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter::apply).collect(Collectors.toList());
    }
    public ExtendedAdDTO createExtendedAdDTO (Ad ad, User user){
        return new ExtendedAdDTO(
                ad.getPk(), //id объявления
                ad.getDescription(),
        ad.getPrice(),
        ad.getTitle(),
                ad.getImage(), // фото товара
                user.getFirstName(),
        user.getLastName(),
                user.getEMail(),
                user.getPhone());
    }
}
