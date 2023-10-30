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
import java.nio.file.AccessDeniedException;

@Service
public interface AdsService {
    public abstract AdsDTO getAdsDTO();

    public abstract ExtendedAdDTO getAdById(int adsId);


    //+++++++++++++++++++++++++++++++++++++++++
    @Transactional
    void deleteAdsById(int adsId, String username) throws IOException;

    Ad createAd(CreateOrUpdateAd createAdDTO,
                MultipartFile image, Authentication authentication
    ) throws IOException;


    //       Обновляет изображение
    //    с заданным
    //    идентификатором.
    AdEntity updateImage(int id, MultipartFile image, String username) throws IOException;

    public abstract AdsDTO getAllAdsByUser(String currentUserName);


    String saveImage(MultipartFile file, int id) throws IOException;

    //    @Override
    //    private Path createPath(MultipartFile image, AdEntity adEntity) throws IOException {
    //        Path filePath = Path.of(adsImageDir, "Объявление_" + adEntity.getId() + "."
    //                + StringUtils.getFilenameExtension(image.getOriginalFilename()));
    //        AccountServiceImpl.uploadImage(image, filePath);
    //        return filePath;
    //    }
    //    static void uploadImage(MultipartFile image, Path filePath) throws IOException {
    //        Files.createDirectories(filePath.getParent());
    //        Files.deleteIfExists(filePath);
    //
    //        try (InputStream is = image.getInputStream();
    //             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
    //             BufferedInputStream bis = new BufferedInputStream(is, 1024);
    //             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
    //        ) {
    //            bis.transferTo(bos);
    //        }
    //    }
    Ad editAdById(int id, CreateOrUpdateAd updateAd, String username) throws AccessDeniedException;
}
