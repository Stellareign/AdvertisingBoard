package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.AdsDTO;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAdDTO;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.interfaces.AdsService;

import java.io.*;

@Slf4j //  добавляет logger в класс
@CrossOrigin(value = "http://localhost:3000") // Позволяет настроить CORS (Cross-Origin Resource Sharing)
// для данного контроллера. Указывает, что этот контроллер может обрабатывать запросы с указанного домена
// (http://localhost:3000), даже если он отличается от домена, на котором запущено приложение.
@RequiredArgsConstructor // генерирует конструктор с аргументами для всех полей, помеченных аннотацией @NonNull
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AdsController {

    private final AdsService adsService;
    //****************************************************
    // получение всех объявлений
    @Operation(summary = "Список всех объявлений")
    @GetMapping
    public ResponseEntity<AdsDTO> getAdsDTO() {
        return ResponseEntity.ok().body(adsService.getAdsDTO());
    }

    //  ********* Без предусловий **********
// **********************************************************************************************
// **********  возвращаю код к состоянию, когда не было авторизации и картинок  *****************
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Создание нового объявлений")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ad> addAd(@RequestPart("image") MultipartFile image,
                                    @RequestPart("properties") CreateOrUpdateAd properties,
            Authentication authentication) throws IOException {
        try {
            log.info("Добавляем новое объявление: " + properties);
//        return ResponseEntity.ok(adsService.createAd(properties, image));
            return ResponseEntity.ok(adsService.createAd2(properties));
        }
catch (IOException e){
    log.info("Ошибка при добавлении объявления");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
}
        }


    // получение информации об объявлении
    @Operation(
            operationId = "getAdById",
            summary = "Получение информации об объявлении по id",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasRole('USER') and @adsService.getAdById(#adId).email == authentication.principal.username")

    @GetMapping("/{adId}")
    public ResponseEntity<ExtendedAdDTO> getAdById(@PathVariable int adId) {
        return ResponseEntity.ok().body(adsService.getAdById(adId));
    }

    // удаление объявления по id
    @Operation(
            operationId = "removeAdById",
            summary = "Удалить объявление по id",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
//    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and" +
//            "@adsService.getAdById(#adId).email == authentication.principal.username)")

    @DeleteMapping("/{adId}")
    public void removeAdById(@PathVariable int adId) {
        adsService.deleteAdsById(adId);
    }

//    @Operation(
//            operationId = "updateAd",
//            summary = "Обновить объявление по id",
//            tags = {"Объявления"},
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "OK"),
//                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
//                    @ApiResponse(responseCode = "403", description = "Forbidden"),
//                    @ApiResponse(responseCode = "404", description = "Not Found")
//            }
//    )
//    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and" +
//            "@adsService.getAdById(#adId).email == authentication.principal.username)")

    @Operation(summary = "Обновление информации об объявлении")
    @PatchMapping("/{adId}")

    public ResponseEntity<Ad> updateAd(@PathVariable int adId, @RequestBody CreateOrUpdateAd updateAd) {

        return ResponseEntity.ok().body(adsService.editAdById(adId, updateAd));
    }

    @Operation(
            operationId = "getCurrentUserAds",
            summary = "Получение всех объявлений авторизованного пользователя",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
            }
    )
//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")

    public ResponseEntity<AdsDTO> getCurrentUserAds(Authentication authentication) {
        return ResponseEntity.ok().body(adsService.getAllAdsByUser(authentication));
    }
    @Operation(
            operationId = "updateImage",
            summary = "Обновление картинки объявления",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
//    @PreAuthorize("hasRole('USER') and @adsService.getAdById(#adId).email == authentication.principal.username")
    @PatchMapping(value = "/{adId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<AdEntity> updateImage(@PathVariable int adId,
                                                @RequestParam("image") MultipartFile image) throws IOException {

        return ResponseEntity.status(HttpStatus.OK).body(adsService.updateImage(adId, image));
    }
}

