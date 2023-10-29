package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.UserDTOFactory;
import ru.skypro.homework.service.interfaces.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


//@WebMvcTest(UserServiceImpl.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @InjectMocks
    private UserDTOFactoryImpl userDTOFactory;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    User user = new User("asd@asd.asd", "Vasya", "Pupkin", "+7(456)456_45-45");

    @Test
    void checkUpdatePasswordTest() {

    }

    @Test
    void getUserForGetController() {
    }

    @Test
    void updateUserTest() {
        String username = "asd@asd.asd";
        String ln = "ln";
        String fn = "fn";
        String phone = "+7(123)456-78-98";

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName(fn);
        updateUserDTO.setLastName(ln);
        updateUserDTO.setPhone(phone);
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setPhone(updateUserDTO.getPhone());

        when(userRepository.findByUsername(username)).thenReturn(user);
        when(userDTOFactory.fromUpdateUserDTOtoUser(updateUserDTO, user)).thenReturn(user);


//        when(userDTOFactory.fromUserToUserDTO(user)).thenReturn(userDTO);
        UserDTO result = userService.updateUser(username, updateUserDTO);


        // Проверяем, что данные пользователя были обновлены
        assertEquals(result.getFirstName(), updateUserDTO.getFirstName());
        assertEquals(result.getLastName(), updateUserDTO.getLastName());
        assertEquals(result.getPhone(), updateUserDTO.getPhone());
        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);

    }

    @Test
    void checkUser() {
    }

    @Test
    void saveRegisterUser() {
    }

    @Test
    void updateUserAvatar() {
    }
}