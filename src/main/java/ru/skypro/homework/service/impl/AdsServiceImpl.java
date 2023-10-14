package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.MapperUtil;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.AdsDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.AdsImage;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.RecordNotFoundException;

import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.AdsImageService;
import ru.skypro.homework.service.interfaces.AdsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final AdsImageService adsImageService;
    private final MapperUtil mapperUtil;
    private final UserRepository userRepository;


    public AdsServiceImpl(AdsRepository adsRepository, AdsImageService adsImageService, MapperUtil mapperUtil, UserRepository userRepository) {
        this.adsRepository = adsRepository;
        this.adsImageService = adsImageService;
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
    }


    @Override
    public AdsDTO getAdsDTO() {
        List<AdEntity> adList = adsRepository.findAll();
        return new AdsDTO(adList.size(), adList);
    }

    @Override
    public ExtendedAdDTO getAdById(int adsId) {
        return mapperUtil.createExtendedAdDTO(adsRepository.findById(adsId).get());
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @Override
    public void deleteAdsById(int adsId) {

        Optional<AdEntity> optionalAds = adsRepository.findById(adsId);
        if (optionalAds.isPresent())
            adsRepository.deleteById(adsId);
    }

    @Override
    public Ad createAd(String title, int price, String description, MultipartFile image) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName(); // имя того, кто давит на клавиши
        User currentUser = mapperUtil.getMapper().map(authentication, User.class);
//                if (currentUser == null) return status(HttpStatus.UNAUTHORIZED).build();
        //               currentUser = userRepository.getById(1); // временно, пока не разберусь с авторизованным юзером
        CreateOrUpdateAdDTO createAdDTO = new CreateOrUpdateAdDTO(title, price, description);
        AdEntity newAd = mapperUtil.createAdfromDTO(createAdDTO, image, currentUser);
        adsRepository.save(newAd);
        return mapperUtil.getMapper().map(newAd, Ad.class);
    }

    @Override
    public Ad editAdById(int id, CreateOrUpdateAdDTO updateAd) {
        Optional<AdEntity> optionalAd = adsRepository.findById(id);
        if (optionalAd.isEmpty()) {
            new RecordNotFoundException(String.valueOf(id));
        }
        AdEntity existingAd = optionalAd.get();
        existingAd.setTitle(updateAd.getTitle());
        existingAd.setPrice(updateAd.getPrice());
        existingAd.setDescription(updateAd.getDescription());
        adsRepository.save(existingAd);
        return mapperUtil.getMapper().map(existingAd, Ad.class);
    }


    //       Обновляет изображение
//    с заданным
//    идентификатором.
//
//             * @param id Идентификатор изображения, которое необходимо обновить.
//     * @param image Файл изображения, который необходимо обновить.
//     * @return Обновленное изображение в виде массива байтов.
//            * @throws IOException Если есть ошибка при чтении файла изображения.
//            * @throws IllegalArgumentException Если файл изображения пуст.
    @Override
    public AdsImage updateImage(int id, MultipartFile image) throws IOException {
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

        if (bFile.length == 0) {
            throw new IllegalArgumentException("Image is Empty");
        }
        return adsImageService.updateImage(id, bFile);
    }

    @Override
    public AdEntity editImageAdById(int id, AdsImage image) {
        Optional<AdEntity> optionalAd = adsRepository.findById(id);
        if (optionalAd.isEmpty()) {
            new RecordNotFoundException(String.valueOf(id));
        }
        AdEntity existingAd = optionalAd.get();
        existingAd.setImage(image);
        adsRepository.save(existingAd);
        return existingAd;
    }

    @Override
    public AdsDTO getAllAdsByUser(Authentication authentication) {
        String currentUserName = authentication.getName();
        List<AdEntity> adList = adsRepository.findAll()
                .stream()
                .filter(e -> e.getAuthor().getUsername().equals(currentUserName))
                .collect(Collectors.toList());
        return new AdsDTO(adList.size(), adList);
    }

    /*
    из разбора с Волковым
    .stream()
                .filter(e -> e.getValue().getIngredients.stream()
                .anyMatch(i -> i.getTitle()/equals(ingrediente.getTitle())))
                .map(e -> RecipeDTO.from(e.getKey(), e.getValue())))
                .collect(Collectors.toList());
     */

}

