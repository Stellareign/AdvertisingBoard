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
            User user = new User();
            user.setUsername("user0@mail.ru");
//            userEntity.setPassword("user0000");
            user.setPassword("$2a$12$szT0GJQE0Zhkq.IB0zuGi.yO.xc8wNjOK42mqrMSL9UQvEjq7jR2C");
            user.setFirstName("testFirstName");
            user.setLastName("testLastName");
            user.setPhone("+79000000000");
            user.setAvatarPath("/users/image/" + user.getId()); // not sure
            user.setRole(Role.USER);
            user.setRegisterDate(LocalDate.now());

            userRepository.save(user);
            return user;
        }

        public AdEntity createTestAd() {
            User userEntity = new User();
            userEntity.setUsername("user0@mail.ru");
            userEntity.setPassword("$2a$12$szT0GJQE0Zhkq.IB0zuGi.yO.xc8wNjOK42mqrMSL9UQvEjq7jR2C");
            userEntity.setFirstName("testFirstName");
            userEntity.setLastName("testLastName");
            userEntity.setPhone("+79000000000");
            userEntity.setAvatarPath("/users/image/" + userEntity.getId()); // not sure
            userEntity.setRole(Role.USER);
            userEntity.setRegisterDate(LocalDate.now());
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
            userEntity.setUsername("user0@mail.ru");
            userEntity.setPassword("$2a$12$szT0GJQE0Zhkq.IB0zuGi.yO.xc8wNjOK42mqrMSL9UQvEjq7jR2C");
            userEntity.setFirstName("testFirstName");
            userEntity.setLastName("testLastName");
            userEntity.setPhone("+79000000000");
            userEntity.setAvatarPath("/users/image/" + userEntity.getId()); // not sure
            userEntity.setRole(Role.USER);
            userEntity.setRegisterDate(LocalDate.now());

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

    }
