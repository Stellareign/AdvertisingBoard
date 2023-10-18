package ru.skypro.homework.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.AdsDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.AdEntity;

import java.io.IOException;
@Service
public interface AdsService {
    public abstract AdsDTO getAdsDTO();

    public abstract ExtendedAdDTO getAdById(int adsId);

    //+++++++++++++++++++++++++++++++++++++++++
    public abstract void deleteAdsById(int adsId);

    public abstract Ad createAd(CreateOrUpdateAd createAdDTO,
                                MultipartFile image
    ) throws IOException;

    public abstract Ad createAd2(CreateOrUpdateAd createAdDTO) throws IOException;

    public abstract Ad editAdById(int id, CreateOrUpdateAd updateAd);

    //       Обновляет изображение
    //    с заданным
    //    идентификатором.
        public abstract AdEntity updateImage(int id, MultipartFile image) throws IOException;

    public abstract AdsDTO getAllAdsByUser(Authentication authentication);
}
