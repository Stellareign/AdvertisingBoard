package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.TestService;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentsControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private TestService testService;

    @AfterEach
    public void resetDb() {
        commentRepository.deleteAll();
        adsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Получение комментариев объявления")
    void shouldReturnCommentCollectionWhenGetCommentsCalled() throws Exception {

        Comment commentEntity = testService.createTestComment();

        mockMvc.perform(MockMvcRequestBuilders.get("/ads/{id}/comments", commentEntity.getAds().getPk())
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + HttpHeaders.encodeBasicAuth("user0@mail.ru",
                                        "user0000", StandardCharsets.UTF_8))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].author").value(commentEntity.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].authorImage").value("/users/image/" + commentEntity.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].authorFirstName").value("testFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].createdAt").value(Matchers.lessThan(Instant.now().toEpochMilli())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].text").value("testText"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Добавление комментария к объявлению")
    void shouldReturnCommentWhenAddCommentCalled() throws Exception {

        CreateOrUpdateCommentDTO createOrUpdateComment = new CreateOrUpdateCommentDTO();
        createOrUpdateComment.setText("createdTestText");

        AdEntity adEntity = testService.createTestAd();

        mockMvc.perform(MockMvcRequestBuilders.post("/ads/{id}/comments", adEntity.getPk())
                        .content(objectMapper.writeValueAsString(createOrUpdateComment))
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + HttpHeaders.encodeBasicAuth("user0@mail.ru",
                                        "user0000", StandardCharsets.UTF_8))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(adEntity.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorImage").value("/users/image/" + adEntity.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorFirstName").value("testFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").value(Matchers.lessThan(Instant.now().toEpochMilli())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("createdTestText"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление комментария по его id")
    void shouldReturnOkWhenDeleteCommentCalled() throws Exception {

        Comment commentEntity = testService.createTestComment();

        mockMvc.perform(MockMvcRequestBuilders.delete("/ads/{adId}/comments/{commentId}",
                                commentEntity.getAds().getPk(), commentEntity.getPk())
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + HttpHeaders.encodeBasicAuth("user0@mail.ru",
                                        "user0000", StandardCharsets.UTF_8)))
                .andExpect(status().isOk());

        Assertions.assertFalse(commentRepository.findById(commentEntity.getPk()).isPresent());
    }

    @Test
    @DisplayName("Обновление комментария")
    void shouldReturnUpdatedCommentWhenUpdateCommentCalled() throws Exception {

        CreateOrUpdateCommentDTO createOrUpdateComment = new CreateOrUpdateCommentDTO();
        createOrUpdateComment.setText("updatedTestText");

        Comment commentEntity = testService.createTestComment();

        mockMvc.perform(MockMvcRequestBuilders.patch("/ads/{adId}/comments/{commentId}",
                                commentEntity.getAds().getPk(), commentEntity.getPk())
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + HttpHeaders.encodeBasicAuth("user0@mail.ru",
                                        "user0000", StandardCharsets.UTF_8))
                        .content(objectMapper.writeValueAsString(createOrUpdateComment))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(commentEntity.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorImage").value("/users/image/" + commentEntity.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorFirstName").value("testFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").value(Matchers.lessThan(Instant.now().toEpochMilli())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("updatedTestText"))
                .andExpect(status().isOk());
    }
}