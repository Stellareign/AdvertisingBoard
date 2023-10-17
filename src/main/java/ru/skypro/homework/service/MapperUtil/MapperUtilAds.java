package ru.skypro.homework.service.MapperUtil;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Data
@NoArgsConstructor
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

    public AdEntity createAdFromDTO(CreateOrUpdateAdDTO inputAd,
                                    MultipartFile image,
                                    User user) throws IOException {
        Path filePath = Path.of("/images/ad_" + image.getOriginalFilename() + "."
                + StringUtils.getFilenameExtension(image.getOriginalFilename()));
        uploadImage(image, filePath);
        return new AdEntity(
                inputAd.getTitle(),
                inputAd.getPrice(),
                inputAd.getDescription(),
                filePath.toString(),
//                "/image",
                user
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
}
