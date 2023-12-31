package ru.skypro.homework.service.interfaces;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.AdsDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.AdEntity;

import java.io.IOException;
import java.rmi.AccessException;

@Service
public interface AdsService {
    public abstract AdsDTO getAdsDTO();

    public abstract ExtendedAdDTO getAdById(int adsId);

    //+++++++++++++++++++++++++++++++++++++++++
    @Transactional
    void deleteAdsById(int adsId) throws IOException;

    Ad createAd(CreateOrUpdateAd createAdDTO,
                MultipartFile image, Authentication authentication
    ) throws IOException;

    Ad editAdById(int id, CreateOrUpdateAd updateAd) throws AccessException;

    AdEntity updateImage(int id, MultipartFile image) throws IOException;

    AdsDTO getAllAdsByUser(String currentUserName);

    String saveImage(MultipartFile file, int id) throws IOException;


    byte[] getAdImageFromFS(int adId) throws IOException;
}
