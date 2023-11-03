package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Role;
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
import ru.skypro.homework.service.interfaces.FileService;


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
    private final FileService fileService;
    private final MapperUtilAds mapperUtil;
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    @Value("${path.to.image.folder}")
    private String adsImageDir;

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
    @Override
    @Transactional
    public void deleteAdsById(int adsId) throws IOException {
        Optional<AdEntity> optionalAds = adsRepository.findById(adsId);

        if (optionalAds.isEmpty()) {
            throw new RecordNotFoundException("Объявление " + adsId + "не найдено");
        }
        commentRepository.deleteCommentsByAds_Pk(adsId);
        adsRepository.deleteById(adsId);                        //Удаляем само объявление
        if (optionalAds.get().getImage() != null && !optionalAds.get().getImage().isEmpty()) {
            Files.deleteIfExists(Path.of(optionalAds.get().getImage()));    //Удаляем файл с картинкой объявления
        }
        log.info("Объявление " + adsId + " удалено");
    }

    @Override
    public boolean checkAccessToAd(int adId, String username) {
        return adsRepository.findByPk(adId).getAuthor().getUsername().equals(username) ||
                userRepository.findByUsername(username).getRole() == Role.ADMIN;
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

    @Override
    public Ad editAdById(int id, CreateOrUpdateAd updateAd) {
        Optional<AdEntity> optionalAd = adsRepository.findById(id);
        if (optionalAd.isEmpty()) {
            throw new RecordNotFoundException("Объявление не найдено " + id);
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
            throw new RecordNotFoundException("Объявление не найдено " + id);
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
        Path filePath = Path.of(adsImageDir,"Фото_объявления_" + id + "."
                + StringUtils.getFilenameExtension(file.getOriginalFilename()));
        String destination = filePath.toString();
//        Files.deleteIfExists(filePath);
//        Files.createDirectories(filePath.getParent());
//        File newFile = new File(filePath.toUri());
//        file.transferTo(newFile);
        fileService.uploadImage(file, filePath);
        return destination;
    }
    /*
     * Выгрузка изображения объявления из файловой системы.<br>
     * - Поиск объявления в базе данных по идентификатору объявления {@link AdRepository#findById(Object)}.<br>
     * - Копирование данных изображения. Входной поток получаем из метода {@link Files#newInputStream(Path, OpenOption...)}
     * @param adId идентификатор объявления в БД
     * @return image - массив байт картинки
     * @throws IOException выбрасывается при ошибках, возникающих во время выгрузки изображения
     */
    @Override
    public byte[] getAdImageFromFS(int adId) throws IOException {
        AdEntity adEntity = adsRepository.findById(adId).orElseThrow(() -> new RecordNotFoundException("AD_NOT_FOUND"));
        byte[] image = fileService.downloadImage(adEntity.getImage());
        log.info("Download advertisement image from database method was invoked.");
        return image;
    }
}



