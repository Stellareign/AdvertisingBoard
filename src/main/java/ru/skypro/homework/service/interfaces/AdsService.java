package ru.skypro.homework.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.AdsDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.AdEntity;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;


@Service
public interface AdsService {

    ExtendedAdDTO getAdById(int adsId);

    void deleteAdsById(int adsId);

    Ad createAd(CreateOrUpdateAd createAdDTO, MultipartFile image) throws IOException;

//    Ad createAd(CreateOrUpdateAdDTO createAdDTO
////                       ,MultipartFile image
//    ) throws IOException;

//    Ad createAd(CreateOrUpdateAdDTO createAdDTO
////                       ,MultipartFile image
//    ) throws IOException;

    Ad createAd2(CreateOrUpdateAd createAdDTO) throws IOException;

    Ad editAdById(int id, CreateOrUpdateAd updateAd)
            throws EntityNotFoundException;

    AdsDTO getAdsDTO();

    //       Обновляет изображение
    //    с заданным
    //    идентификатором.
    AdEntity updateImage(int id, MultipartFile image) throws IOException;

    AdsDTO getAllAdsByUser(Authentication authentication);
}
