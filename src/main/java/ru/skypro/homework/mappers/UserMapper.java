package ru.skypro.homework.mappers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.user.AddUserDTO;
import ru.skypro.homework.entity.User;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper {


    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    private final ModelMapper mapper;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(User.class, AddUserDTO.class) // создание маппинга между классами
               .setPostConverter(toDtoConverter()); // если надо пропустить поле, то перед сет добавить addMappings(m -> m.skip(AddUserDTO::setId)) -
        mapper.createTypeMap(AddUserDTO.class, User.class)
               .setPostConverter(toEntityConverter());
    }

    /*
      public Converter<User, AddUserDTO> toDtoConverter() , пояснения:
      Converter<User, AddUserDTO - анонимный класс для определения логики преобразованя;
      User source = context.getSource(); - получение юзера из контекста преобразования и сохранение в переменную;
      AddUserDTO destination = context.getDestination(); - получение дто из контекста преобразования
      и сохранение в переменную;
      return context.getDestination(); - возвращаем целевой объект из контекста преобразования.
     */
    public Converter<User, AddUserDTO> toDtoConverter() {
        return context -> {
            User source = context.getSource(); // источник
            AddUserDTO result = context.getDestination();
            return context.getDestination();
        };
    }

    public Converter<AddUserDTO, User> toEntityConverter() {
        return context -> {
            AddUserDTO source = context.getSource();
            User result = context.getDestination();
            return context.getDestination();
        };
    }


}
