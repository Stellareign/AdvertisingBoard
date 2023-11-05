package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.TestService;
import ru.skypro.homework.service.interfaces.AuthService;

import java.nio.charset.StandardCharsets;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
    @MockBean
    private AuthService authService;



    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        objectMapper.registerModule(new JavaTimeModule());
    }

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

    @Test
    @DisplayName("Добавление объявления")
    @WithMockUser(value = "user0@mail.ru")
    void shouldReturnAdWhenCreateAdCalled() throws Exception {
        User userEntity = testService.createTestUser();
        CreateOrUpdateAd createOrUpdateAd = new CreateOrUpdateAd("createdTitle", 44444, "createdDescription");
        MockMultipartFile request = new MockMultipartFile(
                "properties",
                "properties",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(createOrUpdateAd).getBytes());
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "image",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "image".getBytes()
        );
        mockMvc.perform(multipart("/ads")
                        .file(request)
                        .file(image))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorId").value(userEntity.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(44444))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("createdTitle"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение информации об объявлении")
    @WithMockUser
    void shouldReturnExtendedAdWhenCalled() throws Exception {
        AdEntity adEntity = testService.createTestAd();
        mockMvc.perform(get("/ads/{id}", adEntity.getPk())
                        .content(objectMapper.writeValueAsString(adEntity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorFirstName").value("testFirstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorLastName").value("testLastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("testDescription"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("user0@mail.ru"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("/ads/image/" + adEntity.getPk()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("+79000000000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(55555))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("testTitle"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Удаление объявления")
    @WithMockUser(value = "user0@mail.ru")
    void shouldReturnOkWhenDeleteAdCalled() throws Exception {
        AdEntity adEntity = testService.createTestAd();
        mockMvc.perform(MockMvcRequestBuilders.delete("/ads/{id}", adEntity.getPk()))
                .andExpect(status().isOk());
        Assertions.assertFalse(adsRepository.findById(adEntity.getPk()).isPresent());
    }

    @Test
    @DisplayName("Обновление информации об объявлении")
    @WithMockUser(value = "user0@mail.ru")
    void shouldReturnUpdatedInfoAboutAdWhenCalled() throws Exception {
        AdEntity adEntity = testService.createTestAd();
        CreateOrUpdateAd createOrUpdateAd = new CreateOrUpdateAd("updatedTitle", 77777, "updatedDescription");
        adEntity.setTitle(createOrUpdateAd.getTitle());
        adEntity.setPrice(createOrUpdateAd.getPrice());
        adEntity.setDescription(createOrUpdateAd.getDescription());
        adsRepository.save(adEntity);
        mockMvc.perform(MockMvcRequestBuilders.patch("/ads/{id}", adEntity.getPk())
                        .content(objectMapper.writeValueAsString(adEntity))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorId").value(adEntity.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("/ads/image/" + adEntity.getPk()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(77777))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("updatedTitle"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Получение объявления авторизованного пользователя")
    @WithMockUser(value = "user0@mail.ru")
    void shouldReturnMyAdsCollectionWhenCalled() throws Exception {
        AdEntity adEntity = testService.createTestAd();
        mockMvc.perform(MockMvcRequestBuilders.get("/ads/me"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].authorId").value(adEntity.getAuthor().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].image").value("/ads/image/" + adEntity.getPk()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].price").value(55555))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].title").value("testTitle"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление картинки объявления")
    @WithMockUser(value = "user0@mail.ru")
    void shouldReturnOkWhenUpdateAdImageCalled() throws Exception {
        AdEntity adEntity = testService.createTestAd();
        MockMultipartFile image = new MockMultipartFile(
                "image", "image", MediaType.MULTIPART_FORM_DATA_VALUE, "image".getBytes()
        );
        mockMvc.perform(multipart(HttpMethod.PATCH, "/ads/{id}/image", adEntity.getPk())
                        .file(image))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        Assertions.assertTrue(adsRepository.findById(adEntity.getPk()).isPresent());
    }
//
//    @Test
//    @DisplayName("Получение картинки объявления")
//    @WithMockUser
//    void shouldReturnImageByteArrayWhenCalled() throws Exception {
//        AdEntity adEntity = testService.createTestAd();
//        mockMvc.perform(get("/ads/image/{adId}", adEntity.getPk()))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").value("/ads/image/" + adEntity.getPk())) // not sure
//                .andExpect(status().isOk());
//    }

}
