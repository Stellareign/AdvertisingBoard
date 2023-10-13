package ru.skypro.homework.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.AdsImage;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.interfaces.AdsImageService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
@Data
@NoArgsConstructor
@Configuration
public class MapperUtil {
    private AdsImageService adsImageService;



    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    public <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter::apply).collect(Collectors.toList());
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

    public AdEntity createAdfromDTO(CreateOrUpdateAdDTO inputAd, MultipartFile image, User user) throws IOException {
            File fileOne = new File("image.getOrignalFileName");//what should be kept inside this method
            byte[] bFile = new byte[(int) fileOne.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(fileOne);
                //convert file into array of bytes
                fileInputStream.read(bFile);
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            AdsImage adsImage = adsImageService.saveImage(bFile);
        return new AdEntity(
                inputAd.getTitle(),
                inputAd.getPrice(),
                inputAd.getDescription(),
                adsImage,
                user
        );
    }

}
