package com.example.demo.domain.imageDomain;

import reactor.core.publisher.Mono;

public interface ImageRepository {
    public Mono<Image> add(Image image);
    public Mono<byte[]> getImageRedis(String uuid);
}
