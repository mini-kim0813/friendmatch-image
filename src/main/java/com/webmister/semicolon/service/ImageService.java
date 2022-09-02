package com.webmister.semicolon.service;

import com.webmister.semicolon.dto.EssentialImage;
import com.webmister.semicolon.repository.ImageRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ImageService {
    final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public Long saveImage(EssentialImage essentialImage) {
        return imageRepository.save(essentialImage.toEntity()).getImageId();
    }
}
