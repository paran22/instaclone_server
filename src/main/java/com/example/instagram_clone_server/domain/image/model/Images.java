package com.example.instagram_clone_server.domain.image.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class Images {
    private List<Image> images;

    public List<String> toImageUrls() {
        return this.images.stream().map(Image::getImageUrl).collect(Collectors.toList());
    }

    public static Images of(List<Image> images) {
        return new Images(images);
    }
}
