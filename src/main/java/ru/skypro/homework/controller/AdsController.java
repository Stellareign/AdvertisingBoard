package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.entity.AdEntity;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    public ResponseEntity<AdsDTO> getAdsDTO() {
        return ResponseEntity.ok().body(adsService.getAdsDTO());
    }

    //  ********* Без предусловий **********
// **********************************************************************************************
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Создание нового объявления")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            , schema = @Schema(implementation = Ad.class)
                    )),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })

    public ResponseEntity<Ad> addAd(@RequestPart("image") MultipartFile image,
                                    @RequestPart("properties") CreateOrUpdateAd properties,
            Authentication authentication) throws IOException {
        try {
            log.info("Добавляем новое объявление: " + properties);
        return ResponseEntity.ok(adsService.createAd(properties, image, authentication));
        }
catch (IOException e){
    log.info("Ошибка при добавлении объявления");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
}
        }


    // получение информации об объявлении
    @Operation(summary = "Получение информации об объявлении по id")
    @GetMapping("/{adId}")
            @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "404", description = "Not Found")
})
    public ResponseEntity<ExtendedAdDTO> getAdById(@PathVariable int adId) {
        return ResponseEntity.ok().body(adsService.getAdById(adId));
    }

    // удаление объявления по id
    @Operation(summary = "Удалить объявление по id")
    @PreAuthorize("hasRole('ADMIN') or " +
            "@adsService.getAdById(#adId).email == authentication.principal.username")
    @DeleteMapping("/{adId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public void removeAdById(@PathVariable int adId) throws IOException {
                adsService.deleteAdsById(adId);
    }

    @Operation(summary = "Обновить объявление по id")
    @PreAuthorize("hasRole('ADMIN') or " +
            "@adsService.getAdById(#adId).email == authentication.principal.username")
    @PatchMapping("/{adId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<Ad> updateAd(@PathVariable int adId, @RequestBody CreateOrUpdateAd updateAd) {

        return ResponseEntity.ok().body(adsService.editAdById(adId, updateAd));
    }

    @Operation( summary = "Получение всех объявлений авторизованного пользователя")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/me")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<AdsDTO> getCurrentUserAds(Authentication authentication) {
        return ResponseEntity.ok().body(adsService.getAllAdsByUser(authentication));
    }

    @Operation(summary = "Обновление картинки объявления")
    @PreAuthorize("hasRole('ADMIN') and @adsService.getAdById(#adId).email == authentication.principal.username")
    @PatchMapping(value = "/{adId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<AdEntity> updateImage(@PathVariable int adId,
                                                @RequestParam("image") MultipartFile image) throws IOException {

        return ResponseEntity.status(HttpStatus.OK).body(adsService.updateImage(adId, image));
    }
}

