package com.webmister.semicolon.dto;

import com.webmister.semicolon.domain.Image;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EssentialImage {
    private String originImageName;
    private String imageName;
    private String imagePath;

    public Image toEntity() {
        Image build = Image.builder()
                .originImageName(originImageName)
                .imagePath(imagePath)
                .imageName(imageName)
                .build();
        return build;
    }

    @Builder
    public EssentialImage(String originImageName, String imagePath, String imageName) {
        this.originImageName = originImageName;
        this.imagePath = imagePath;
        this.imageName = imageName;
    }
}
