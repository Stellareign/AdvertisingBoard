package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.AdsDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.RecordNotFoundException;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.MapperUtil.MapperUtilAds;
import ru.skypro.homework.service.interfaces.AdsService;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final MapperUtilAds mapperUtil;
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
//    @Value("${path.to.image.folder}")
//    private String adsImageDir;

    @Override
    public AdsDTO getAdsDTO() {
        List<Ad> adList = mapperUtil.convertListAdEntityToAd(adsRepository.findAll());
        return new AdsDTO(adList.size(), adList);
    }

    @Override
    public ExtendedAdDTO getAdById(int adsId) {
        Optional<AdEntity> optionalAds = adsRepository.findById(adsId);
        if (optionalAds.isEmpty()) {
            throw new RecordNotFoundException("Не удалось найти объявление с id =  " + adsId);
        }
        return mapperUtil.createExtendedAdDTO(optionalAds.get());
    }

    //+++++++++++++++++++++++++++++++++++++++++
    private final CheckAccess checkAccess;
    @Override
    @Transactional

    public void deleteAdsById(int adsId) throws IOException {
        Optional<AdEntity> optionalAds = adsRepository.findById(adsId);
        if (optionalAds.isPresent()) {
            commentRepository.deleteCommentsByAds_Pk(adsId);
            adsRepository.deleteById(adsId);                        //Удаляем само объявление
            if (optionalAds.get().getImage() != null && !optionalAds.get().getImage().isEmpty()) {
                Files.deleteIfExists(Path.of(optionalAds.get().getImage()));    //Удаляем файл с картинкой объявления
            }
            log.info("Объявление " + adsId + " удалено");
        } else {
            throw new RecordNotFoundException("Объявление не найдено");
        }
    }


    @Override
    public Ad createAd(CreateOrUpdateAd createAdDTO,
                       MultipartFile image, Authentication authentication
    ) throws IOException {
        User currentUser = userRepository.findByUsername(authentication.getName());
        AdEntity newAd = mapperUtil.createAdFromDTO(createAdDTO, "", currentUser);
        adsRepository.save(newAd);
        int pk = newAd.getPk();
        String imagePath = saveImage(image, pk);
        newAd.setImage(imagePath);
        adsRepository.save(newAd);
        return modelMapper.map(newAd, Ad.class);
    }
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
    @Override
    public Ad editAdById(int id, CreateOrUpdateAd updateAd) {
        Optional<AdEntity> optionalAd = adsRepository.findById(id);
        if (optionalAd.isEmpty()) {
            throw new RecordNotFoundException("Не удалось найти объявление с id =  " + id);
        }
        AdEntity existingAd = optionalAd.get();
        existingAd.setTitle(updateAd.getTitle());
        existingAd.setPrice(updateAd.getPrice());
        existingAd.setDescription(updateAd.getDescription());
        adsRepository.save(existingAd);
        return modelMapper.map(existingAd, Ad.class);
    }
//       Обновляет изображение
//    с заданным
//    идентификатором.
    @Override
    public AdEntity updateImage(int id, MultipartFile image) throws IOException {
        Optional<AdEntity> optionalAd = adsRepository.findById(id);
        if (optionalAd.isEmpty()) {
            throw new RecordNotFoundException(String.valueOf(id));
        }
        AdEntity existingAd = optionalAd.get();
        existingAd.setImage(saveImage(image, id));
        adsRepository.save(existingAd);
        return existingAd;
    }

    @Override
    public AdsDTO getAllAdsByUser(String currentUserName) {

        List<AdEntity> adEntityList = adsRepository.findAll()
                .stream()
                .filter(e -> e.getAuthor().getUsername().equals(currentUserName))
                .collect(Collectors.toList());
        List<Ad> adList = mapperUtil.convertListAdEntityToAd(adEntityList);
        return new AdsDTO(adList.size(), adList);
    }
    @Override
public String saveImage(MultipartFile file, int id) throws IOException {
        Path filePath = Path.of("/images/Фото_объявления_" + id + "."
                + StringUtils.getFilenameExtension(file.getOriginalFilename()));
        String destination = filePath.toString();
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        File newFile = new File(filePath.toUri());
    file.transferTo(newFile);
    return destination;
}

}
    /*
    из разбора с Волковым
    .stream()
                .filter(e -> e.getValue().getIngredients.stream()
                .anyMatch(i -> i.getTitle()/equals(ingrediente.getTitle())))
                .map(e -> RecipeDTO.from(e.getKey(), e.getValue())))
                .collect(Collectors.toList());
     */



