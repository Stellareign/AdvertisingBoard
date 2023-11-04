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
import java.nio.file.OpenOption;
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

    /**
     * Получение оюъявления из БД по id
     *
     * @param adsId - id объявления
     * @return Может выбрасывать исключение:
     * @throws RecordNotFoundException
     * @see AdsRepository#findById(Object)
     */
    @Override
    public ExtendedAdDTO getAdById(int adsId) {
        Optional<AdEntity> optionalAds = adsRepository.findById(adsId);
        if (optionalAds.isEmpty()) {
            throw new RecordNotFoundException("Не удалось найти объявление с id =  " + adsId);
        }
        return mapperUtil.createExtendedAdDTO(optionalAds.get());
    }

    //+++++++++++++++++++++++++++++++++++++++++

    /**
     * Удаление объявления из БД по id
     *
     * @param adsId - id объявления
     * @throws IOException
     */
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

    /**
     * Метод создает новое объявление на основе данных, полученных из объекта CreateOrUpdateAd,
     * сохраняет его в БД и присваивает ему уникальный идентификатор. Также метод сохраняет
     * изображение, полученное в виде MultipartFile, в папку с именем, соответствующим идентификатору
     * созданного объявления, и присваивает путь к изображению в качестве значения поля "image" в объекте AdEntity.
     * В конце метод возвращает объект Ad, созданный на основе сохранённого в базе данных объявления.
     *
     * @param createAdDTO    объект CreateOrUpdateAd, содержащий данные для создания объявления
     * @param image          изображение объявления в формате MultipartFile
     * @param authentication объект Authentication, содержащий информацию о текущем пользователе
     * @return объект Ad, созданный на основе сохраненного в БД объявления
     * @throws IOException если возникла ошибка при сохранении изображения
     */
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

    /**
     * Метод для редактирования объявления по его id.
     *
     * @param id       уникальный идентификатор объявления
     * @param updateAd объект класса CreateOrUpdateAd с обновленными данными для объявления
     * @return объект класса Ad, представляющий отредактированное объявление
     * @throws RecordNotFoundException если объявление с указанным id не найдено в БД
     */
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

    /**
     * Обновление изображения для объявления с указанным id.
     *
     * @param id    идентификатор объявления
     * @param image новое изображение для объявления
     * @return обновленное объявление с новым изображением
     * @throws IOException             если возникла ошибка при сохранении изображения
     * @throws RecordNotFoundException если объявление с указанным id не найдено
     */
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

    /**
     * Метод получения всех объявлений пользователя по его логику (email).
     *
     * @param currentUserName имя пользователя
     * @return объект класса AdsDTO, содержащий количество объявлений и список объявлений пользователя
     */
    @Override
    public AdsDTO getAllAdsByUser(String currentUserName) {

        List<AdEntity> adEntityList = adsRepository.findAll()
                .stream()
                .filter(e -> e.getAuthor().getUsername().equals(currentUserName))
                .collect(Collectors.toList());
        List<Ad> adList = mapperUtil.convertListAdEntityToAd(adEntityList);
        return new AdsDTO(adList.size(), adList);
    }

    /**
     * Метод сохранения изображения в определенной директории на сервере.
     *
     * @param file объект класса MultipartFile, содержащий загружаемое изображение
     * @param id   идентификатор объявления, к которому привязано изображение
     * @return строку с путём к сохраненному изображению
     * @throws IOException выбрасывается при ошибке ввода-вывода
     */
    @Override
    public String saveImage(MultipartFile file, int id) throws IOException {
        Path filePath = Path.of(adsImageDir, "Фото_объявления_" + id + "."
                + StringUtils.getFilenameExtension(file.getOriginalFilename()));
        String destination = filePath.toString();
        fileService.uploadImage(file, filePath);
        return destination;
    }

    /**
     * Выгрузка изображения объявления из файловой системы.<br>
     * - Поиск объявления в базе данных по идентификатору объявления {@link AdsRepository#findById(Object)}.<br>
     * - Копирование данных изображения. Входной поток получаем из метода {@link Files#newOutputStream(Path, OpenOption...)}
     *
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
    /*
    из разбора с Волковым
    .stream()
                .filter(e -> e.getValue().getIngredients.stream()
                .anyMatch(i -> i.getTitle()/equals(ingrediente.getTitle())))
                .map(e -> RecipeDTO.from(e.getKey(), e.getValue())))
                .collect(Collectors.toList());
     */



