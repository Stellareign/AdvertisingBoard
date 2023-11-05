package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TestService {
        private final AdsRepository adsRepository;
        private final CommentRepository commentRepository;
        private final UserRepository userRepository;
        //Создаем "подопытных" Юзера, Объявление и Комментарий
        public User createTestUser() {
//            userEntity.setPassword("user0000");
            User userEntity = new User(
                    "user0@mail.ru",
                    "testFirstName",
                    "testLastName",
                    Role.USER,
                    "+79000000000",
                    "",
                    LocalDate.now());  // Создаём через конструктор, чтоб появился id
            userEntity.setPassword("$2a$12$szT0GJQE0Zhkq.IB0zuGi.yO.xc8wNjOK42mqrMSL9UQvEjq7jR2C");
            userRepository.save(userEntity);
            userEntity.setAvatarPath("/users/image/" + userEntity.getId());
            userRepository.save(userEntity);
            return userEntity;
        }

        public AdEntity createTestAd() {
            User userEntity = createTestUser();

            AdEntity adEntity = new AdEntity(
            "testTitle",
            55555,
            "testDescription",
            "",
            userEntity);  // Создаём через конструктор, чтоб появился pk
            adsRepository.save(adEntity); // pk появляется только после сохранения в БД
            adEntity.setImage("/ads/image/" + adEntity.getPk());
            adsRepository.save(adEntity);
            return adEntity;
        }

        public Comment createTestComment() {
            AdEntity adEntity = createTestAd();

            Comment commentEntity = new Comment();
            commentEntity.setText("testText");
            commentEntity.setCreatedAt(Instant.now().toEpochMilli()); // or we can use System.currentTimeMillis()
            commentEntity.setAuthor(adEntity.getAuthor());
            commentEntity.setAds(adEntity);
            commentRepository.save(commentEntity);
            return commentEntity;
        }
    }
