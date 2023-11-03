package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.homework.dto.user.UpdatePasswordDTO;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.TestService;
import ru.skypro.homework.service.interfaces.UserService;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
     @Autowired
        private MockMvc mockMvc;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private ObjectMapper objectMapper;
        @Autowired
    UserService userService;
        @Autowired
        private TestService testService;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @AfterEach
        public void clearDB() {
            userRepository.deleteAll();
        }

        @Test
        @DisplayName("Обновление пароля")
        void shouldReturnOkWhenUpdatePasswordCalled() throws Exception {

            User userEntity = testService.createTestUser();

            UpdatePasswordDTO newPassword = new UpdatePasswordDTO();
            newPassword.setCurrentPassword("user0000");
            newPassword.setNewPassword("newTestPassword");

            mockMvc.perform(MockMvcRequestBuilders.post("/users/set_password")
                            .content(objectMapper.writeValueAsString(newPassword))
                            .header(HttpHeaders.AUTHORIZATION,
                                    "Basic " + HttpHeaders.encodeBasicAuth("user0@mail.ru",
                                            "user0000", StandardCharsets.UTF_8))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());

            Assertions.assertTrue(userRepository.findByUsername(userEntity.getUsername()) != null);
            Assertions.assertTrue(passwordEncoder.matches("newTestPassword",
                    userRepository.findByUsername(userEntity.getUsername()).getPassword()));
        }

        @Test
        @DisplayName(value = "Получение информации об авторизованном пользователе")
        void shouldReturnInfoAboutUserWhenCalled() throws Exception {

            User userEntity = testService.createTestUser();

            mockMvc.perform(MockMvcRequestBuilders.get("/users/me")
                            .header(HttpHeaders.AUTHORIZATION,
                                    "Basic " + HttpHeaders.encodeBasicAuth("user0@mail.ru",
                                            "user0000", StandardCharsets.UTF_8)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("user0@mail.ru"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("testFirstName"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("testLastName"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("+79000000000"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.avatar").value("/users/image/" + userEntity.getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(userEntity.getRole().name()))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName(value = "Обновление информации об авторизованном пользователе")
        void shouldReturnUpdatedInfoAboutUserWhenCalled() throws Exception {

            User user = testService.createTestUser();

            UpdateUserDTO updatedUser = new UpdateUserDTO();
            updatedUser.setFirstName("updatedFirstName");
            updatedUser.setLastName("updatedLastName");
            updatedUser.setPhone("+79555555555");
//            user.setFirstName(updatedUser.getFirstName());
//            user.setLastName(updatedUser.getLastName());
//            user.setPhone(updatedUser.getPhone());
////            (userService.updateUser(authentication.getName(), updateUserDTO))
            mockMvc.perform(MockMvcRequestBuilders.patch("/users/me")
                            .header(HttpHeaders.AUTHORIZATION,
                                    "Basic " + HttpHeaders.encodeBasicAuth("user0@mail.ru",
                                            "user0000", StandardCharsets.UTF_8))
  //                          .content(objectMapper.userService.updateUser(updatedUser))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(updatedUser.getLastName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("+79555555555"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName(value = "Обновление аватара авторизованного пользователя")
        void shouldReturnOkWhenUpdateUserAvatarCalled() throws Exception {

            User userEntity = testService.createTestUser();

            MockMultipartFile image = new MockMultipartFile(
                    "image", "image", MediaType.MULTIPART_FORM_DATA_VALUE, "image".getBytes()
            );

            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PATCH, "/users/me/image")
                            .file(image)
                            .header(HttpHeaders.AUTHORIZATION,
                                    "Basic " + HttpHeaders.encodeBasicAuth("user0@mail.ru",
                                            "user0000", StandardCharsets.UTF_8)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk());

            Assertions.assertTrue(userRepository.findById(userEntity.getId()).isPresent());
        }

        @Test // under construction
        @DisplayName(value = "Получение аватара пользователя по его id")
        void shouldReturnImageByteArrayWhenCalled() throws Exception {

            User userEntity = testService.createTestUser();

            mockMvc.perform(MockMvcRequestBuilders.get("/users/image/{userId}", userEntity.getId())
                            .header(HttpHeaders.AUTHORIZATION,
                                    "Basic " + HttpHeaders.encodeBasicAuth("user0@mail.ru",
                                            "user0000", StandardCharsets.UTF_8)))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.jsonPath("$").value("/users/image/" + userEntity.getId())) // not sure
                    .andExpect(status().isOk());
        }
}