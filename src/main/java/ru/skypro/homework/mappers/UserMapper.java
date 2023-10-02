package ru.skypro.homework.mappers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.User;

import javax.annotation.PostConstruct;

@Component
public class UserMapper {

    private final ModelMapper mapper;
    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }


    @PostConstruct
    public void setupMapper() {
        // регистрация юзера
        mapper.createTypeMap(User.class, UserDTO.class) // создание маппинга между классами
                .setPostConverter(userToDtoConverter()); // если надо пропустить поле, то перед сет добавить поле типа addMappings(m -> m.skip(AddUserDTO::setId))
        mapper.createTypeMap(UserDTO.class, User.class)
                .setPostConverter(userToEntityConverter())
                .addMappings(m -> m.skip(User::setCurrentPassword));
        // смена пароля

        // обновление юзера
        mapper.createTypeMap(User.class, UpdateUserDTO.class) // создание маппинга между классами
                .setPostConverter(UpdUserToDtoConverter());
        mapper.createTypeMap(UpdateUserDTO.class, User.class)
                .setPostConverter(UpdUserToEntityConverter());
    }

    /*
      public Converter<User, AddUserDTO> passToDtoConverter() , пояснения:
      Converter<User, AddUserDTO - анонимный класс для определения логики преобразованя;
      User source = context.getSource(); - получение юзера из контекста преобразования и сохранение в переменную;
      AddUserDTO destination = context.getDestination(); - получение дто из контекста преобразования
      и сохранение в переменную;
      return context.getDestination(); - возвращаем целевой объект из контекста преобразования.
     */
    public Converter<User, UserDTO> userToDtoConverter() {
        return context -> {
            User source = context.getSource(); // источник
            UserDTO result = context.getDestination();
            return context.getDestination();
        };
    }

    public Converter<UserDTO, User> userToEntityConverter() {
        return context -> {
            UserDTO source = context.getSource();
            User result = context.getDestination();
            return context.getDestination();
        };
    }

    public Converter<User, UpdateUserDTO> UpdUserToDtoConverter() {
        return context -> {
            User source = context.getSource(); // источник
            UpdateUserDTO result = context.getDestination();
            return context.getDestination();
        };
    }

    public Converter<UpdateUserDTO, User> UpdUserToEntityConverter() {
        return context -> {
            UpdateUserDTO source = context.getSource();
            User result = context.getDestination();
            return context.getDestination();
        };
    }

}
