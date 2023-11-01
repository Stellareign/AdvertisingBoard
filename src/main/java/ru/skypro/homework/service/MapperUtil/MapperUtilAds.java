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
public class MapperUtilAds {

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    public ExtendedAdDTO createExtendedAdDTO(AdEntity ad) {
        User user = ad.getAuthor();
        return new ExtendedAdDTO(
                ad.getPk(), //id объявления
                ad.getDescription(),
                ad.getPrice(),
                ad.getTitle(),
                ad.getImage(), // фото товара
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPhone());
    }
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

    public Ad createAdFromEntity(AdEntity adEntity) throws IOException {

        return new Ad(
                adEntity.getPk(),                     //'id объявления'
                adEntity.getPrice(),                // 'цена объявления'
                adEntity.getTitle(),                // 'заголовок объявления'
                adEntity.getAuthor().getId(),           // автор объявления
                adEntity.getImage()                 // photo
        );
    }
    public void uploadImage(MultipartFile image, Path filePath) throws IOException {
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = image.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
    }
    public List<Ad> convertListAdEntityToAd(List<AdEntity> adEntityList) {
        List<Ad> listAd = new ArrayList<>();

        for (AdEntity adEntity : adEntityList) {
            Ad ad = new Ad();
            ad.setPk(adEntity.getPk());                     //id объявления
            ad.setPrice(adEntity.getPrice());               // 'цена объявления'
            ad.setTitle(adEntity.getTitle());               // 'заголовок объявления'
            ad.setAuthorId(adEntity.getAuthor().getId());   // id автора объявления
            ad.setImage(adEntity.getImage());               //'ссылка на картинку объявления'
            listAd.add(ad);
        }
        return listAd;
    }
}
