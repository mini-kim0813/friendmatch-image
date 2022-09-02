package com.webmister.semicolon.controller;

import com.webmister.semicolon.dto.EssentialImage;
import com.webmister.semicolon.service.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class ImageController {
    final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("{userNickname}/profileImageUp")
    public String imageUpload(@RequestParam(name = "image") MultipartFile image,
                              @PathVariable("userNickname") String userNickname) {

        LocalDateTime now = LocalDateTime.now();

        String absolutePath = new File("C:/Users/JBTS-qwe/Desktop").getAbsolutePath() + "/";
        String newUserImage = userNickname + now.getHour() + now.getMinute();
        String fileExtension = '.' + image.getOriginalFilename().replaceAll("^.*\\.(.*)$", "$1");
        String path = "images/" + now.getYear() + "/" + now.getMonthValue() + "/" + now.getDayOfMonth();

        try {
            if(!image.isEmpty()) {
                File file = new File(absolutePath + path);
                if(!file.exists()){
                    file.mkdirs();
                }

                file = new File(absolutePath + path + "/" + newUserImage + fileExtension);
                image.transferTo(file);

                EssentialImage imgDto = EssentialImage.builder()
                        .originImageName(image.getOriginalFilename())
                        .imagePath(path)
                        .imageName(newUserImage + fileExtension)
                        .build();

                imageService.saveImage(imgDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Hello";
    }
}
