package ru.skypro.homework.service.MapperUtil;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Data
@NoArgsConstructor
//@Component
@Configuration
/**
 * Класс, содержащий методы преобразования сущности AdEntity в DTO и обратно
 */
public class MapperUtilAds {

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    /**
     * Метод, создающий объект класса ExtendedAdDTO на основе сущности AdEntity.
     *
     * @param ad сущность объявления
     * @return объект класса ExtendedAdDTO
     */
    public ExtendedAdDTO createExtendedAdDTO(AdEntity ad) {
        User user = ad.getAuthor();
        return new ExtendedAdDTO(
                ad.getPk(), //id объявления
                ad.getDescription(),
                ad.getPrice(),
                ad.getTitle(),
                ("/ads/image/" + ad.getPk()), // фото товара
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPhone());
    }

    /**
     * Метод создаёт новое объявление на основе данных, полученных из объекта CreateOrUpdateAd.
     * Также в качестве параметров принимает изображение и пользователя, которые будут связаны с новым объявлением.
     *
     * @param inputAd объект типа CreateOrUpdateAd, содержащий данные для создания нового объявления
     * @param image   изображение, которое будет связано с новым объявлением
     * @param user    пользователь, который будет связан с новым объявлением
     * @return объект типа AdEntity, содержащий данные о новом объявлении
     * @throws IOException если возникла ошибка при работе с файлом изображения
     */
    public AdEntity createAdFromDTO(CreateOrUpdateAd inputAd,
                                    String image,
                                    User user) throws IOException {
        return new AdEntity(
                inputAd.getTitle(),
                inputAd.getPrice(),
                inputAd.getDescription(),
                image,
                user
        );
    }

    /**
     * Создаёт объект класса Ad на основе сущности AdEntity, содержащей информацию о конкретном объявлении.
     *
     * @param adEntity сущность объявления, содержащая информацию о цене, заголовке, авторе и фото товара
     * @return объект класса Ad с заполненными полями на основе переданной сущности
     * @throws IOException если возникла ошибка при обработке фото товара
     */
    public Ad createAdFromEntity(AdEntity adEntity) throws IOException {

        return new Ad(
                adEntity.getPk(),                     //'id объявления'
                adEntity.getPrice(),                // 'цена объявления'
                adEntity.getTitle(),                // 'заголовок объявления'
                adEntity.getAuthor().getId(),           // автор объявления
                ("/ads/image/" + adEntity.getPk()) // фото товара
        );
    }

    /**
     * Метод преобразует список объектов типа AdEntity в список объектов типа Ad.
     *
     * @param adEntityList список объектов типа AdEntity
     * @return список объектов типа Ad
     */
    public List<Ad> convertListAdEntityToAd(List<AdEntity> adEntityList) {
        List<Ad> listAd = new ArrayList<>();

        for (AdEntity adEntity : adEntityList) {
            Ad ad = new Ad();
            ad.setPk(adEntity.getPk());                     //id объявления
            ad.setPrice(adEntity.getPrice());               // 'цена объявления'
            ad.setTitle(adEntity.getTitle());               // 'заголовок объявления'
            ad.setAuthorId(adEntity.getAuthor().getId());   // id автора объявления
            ad.setImage(("/ads/image/" + adEntity.getPk()));               //'ссылка на картинку объявления'
            listAd.add(ad);
        }
        return listAd;
    }
}
