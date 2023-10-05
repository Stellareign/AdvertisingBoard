package ru.skypro.homework.mappers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.entity.User;

import javax.annotation.PostConstruct;

@Component
public class PasswordMapper {


    private final ModelMapper mapper;

    public PasswordMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

//    @PostConstruct
//    public void setupMapper() {
//        mapper.createTypeMap(User.class, UpdatePasswordDTO.class) // создание маппинга между классами
//                .setPostConverter(passToDtoConverter());
//        mapper.createTypeMap(UpdatePasswordDTO.class, User.class)
//                // пропуск маппинга полей юзера:
//                .setPostConverter(passToEntityConverter(UpdatePasswordDTO)
//                .addMappings(m -> m.skip(User::setFirstName))
//                .addMappings(m -> m.skip(User::setLastName))
//                .addMappings(m -> m.skip(User::setEmail))
//                .addMappings(m -> m.skip(User::setImage))
//                .addMappings(m -> m.skip(User::setRole));
//    }
    public UpdatePasswordDTO passToDtoConverter(User user) {
        return mapper.map(user, UpdatePasswordDTO.class);
    }
//    public Converter<User, UpdatePasswordDTO> passToDtoConverter() {
//        return context -> {
//            User source = context.getSource(); // источник
//            UpdatePasswordDTO result = context.getDestination();
//            result.setCurrentPassword(source.getCurrentPassword()); // присваиваем полю currentPassword поле password
//            return result;
//        };
//    }

    public User passToEntityConverter(UpdatePasswordDTO updatePasswordDTO) {
        return mapper.map(updatePasswordDTO, User.class);
    }
//    public Converter<UpdatePasswordDTO, User> passToEntityConverter() {
//        return context -> {
//            UpdatePasswordDTO source = context.getSource();
//            User result = context.getDestination();
//            result.setCurrentPassword(source.getNewPassword()); // присваиваем полю password поле newPassword (??? - возможно надо будет изменить политику)
//            return result;
//        };
//    }
}
