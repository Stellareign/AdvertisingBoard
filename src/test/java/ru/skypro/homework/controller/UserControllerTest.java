package ru.skypro.homework.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserControllerTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private Authentication authentication;
    @Test
    void setPasswordTest() {
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setCurrentPassword("oldPassword");
        updatePasswordDTO.setNewPassword("newPassword");

        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userService.checkUpdatePassword(updatePasswordDTO, username)).thenReturn(true);
        when(authentication.getName()).thenReturn(username);
        when(userService.getUserForGetController(username)).thenReturn(user);

        // when
        ResponseEntity<UpdatePasswordDTO> responseEntity = user.setPassword(updatePasswordDTO, authentication);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatePasswordDTO, responseEntity.getBody());
    }

    @Test
    void getUserTest() {
    }

    @Test
    void updateUserTest() {
    }

    @Test
    void updateUserImageTest() {
    }

    @Test
    void getImageTest() {
    }
}