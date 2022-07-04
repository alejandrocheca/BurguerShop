package com.example.demo.application.imageApplication;

import reactor.core.publisher.Mono;

public class ImageApplication {
    public Mono<ImageDTO> add(CreateOrUpdateImageDTO dto);
    public Mono<ImageDTO> getImageRedis(String id);
}
