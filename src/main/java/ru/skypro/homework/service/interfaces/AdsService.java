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

    boolean checkAccessToAd(int adId, String username);

    Ad createAd(CreateOrUpdateAd createAdDTO,
                MultipartFile image, Authentication authentication
    ) throws IOException;



    Ad editAdById(int id, CreateOrUpdateAd updateAd) throws AccessException;


    //       Обновляет изображение
//    с заданным
//    идентификатором.
    AdEntity updateImage(int id, MultipartFile image) throws IOException;

    AdsDTO getAllAdsByUser(String currentUserName);

    String saveImage(MultipartFile file, int id) throws IOException;

    /*
     * Выгрузка изображения объявления из файловой системы.<br>
     * - Поиск объявления в базе данных по идентификатору объявления {@link AdRepository#findById(Object)}.<br>
     * - Копирование данных изображения. Входной поток получаем из метода {@link Files#newInputStream(Path, OpenOption...)}
     * @param adId идентификатор объявления в БД
     * @return image - массив байт картинки
     * @throws IOException выбрасывается при ошибках, возникающих во время выгрузки изображения
     */
    byte[] getAdImageFromFS(int adId) throws IOException;
}
