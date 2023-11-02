package ru.skypro.homework.service.interfaces;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
@Service
@RequiredArgsConstructor
public class AdsServiceTest {
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    //Создаем "подопытных" Юзера, Объявление и Комментарий
    public User createTestUser() {
        User user = new User();
        user.setUsername("testEmail@mail.ru");
        user.setPassword("password");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPhone("+79222222222");
        user.setAvatarPath("/users/avatar/" + user.getId()); // not sure
        user.setRole(Role.USER);
        userRepository.save(user);
        return user;
    }

    public AdEntity createTestAd() {
        User userEntity = new User();
        userEntity.setUsername("testEmail@mail.ru");
        userEntity.setPassword("password");
        userEntity.setFirstName("testFirstName");
        userEntity.setLastName("testLastName");
        userEntity.setPhone("+79222222222");
        userEntity.setAvatarPath("/users/avatar/" + userEntity.getId()); // not sure
        userEntity.setRole(Role.USER);
        userRepository.save(userEntity);


        AdEntity adEntity = new AdEntity();
        adEntity.setDescription("testDescription");
        adEntity.setPrice(55555);
        adEntity.setTitle("testTitle");
        adEntity.setImage("/ads/image/" + adEntity.getPk()); // not sure
        adEntity.setAuthor(userEntity);
        adsRepository.save(adEntity);
        return adEntity;
    }

    public Comment createTestComment() {
        User userEntity = new User();
        userEntity.setUsername("testEmail@mail.ru");
        userEntity.setPassword("password");
        userEntity.setFirstName("testFirstName");
        userEntity.setLastName("testLastName");
        userEntity.setPhone("+79222222222");
        userEntity.setAvatarPath("/users/avatar/" + userEntity.getId()); // not sure
        userEntity.setRole(Role.USER);
        userRepository.save(userEntity);

        AdEntity adEntity = new AdEntity();
        adEntity.setDescription("testDescription");
        adEntity.setPrice(55555);
        adEntity.setTitle("testTitle");
        adEntity.setImage("/ads/image/" + adEntity.getPk()); // not sure
        adEntity.setAuthor(userEntity);
        adsRepository.save(adEntity);

        Comment commentEntity = new Comment();
        commentEntity.setText("testText");
        commentEntity.setCreatedAt(Instant.now().toEpochMilli()); // or we can use System.currentTimeMillis()
        commentEntity.setAuthor(userEntity);
        commentEntity.setAds(adEntity);
        commentRepository.save(commentEntity);
        return commentEntity;
    }
    @Test
    void getAdsDTO() {
    }

    @Test
    void getAdById() {
    }

    @Test
    void deleteAdsById() {
    }

    @Test
    void checkAccessToAd() {
    }

    @Test
    void createAd() {
    }

    @Test
    void editAdById() {
    }

    @Test
    void updateImage() {
    }

    @Test
    void getAllAdsByUser() {
    }

    @Test
    void saveImage() {
    }

    @Test
    void getAdImageFromFS() {
    }
}