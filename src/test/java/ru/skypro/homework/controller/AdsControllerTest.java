package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.TestService;
import ru.skypro.homework.service.interfaces.AuthService;

import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull
class AdsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private TestService testService;
//    @MockBean
//    private AuthService authService;
//    @BeforeEach
//    public void init() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .apply(springSecurity())
//                .build();
//    }

    @AfterEach
    public void clearDB() {
        adsRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    @DisplayName("Получение всех объявлений")
    void shouldReturnAdsCollectionWhenCalled() throws Exception {
        AdEntity adEntity = testService.createTestAd();
        mockMvc.perform(MockMvcRequestBuilders.get("/ads")
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + HttpHeaders.encodeBasicAuth("user0@mail.ru",
                                        "user0000", StandardCharsets.UTF_8))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].authorId").value(adEntity.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].image").value("/ads/image/" + adEntity.getPk()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].price").value(55555))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].title").value("testTitle"))
                .andExpect(status().isOk());
    }

//    @Test
//    void addAd() {
//    }
//
//    @Test
//    void getAdById() {
//    }
//
//    @Test
//    void removeAdById() {
//    }
//
//    @Test
//    void updateAd() {
//    }
//
//    @Test
//    void getCurrentUserAds() {
//    }
//
//    @Test
//    void updateImage() {
//    }
//
//    @Test
//    void getAdImageFromFS() {
//    }
}