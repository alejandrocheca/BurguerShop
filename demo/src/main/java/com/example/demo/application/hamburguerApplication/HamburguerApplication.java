package com.example.demo.application.hamburguerApplication;
import com.example.demo.hamburgerDomain.Hamburguer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class HamburguerApplication {
    public Mono<HamburguerDTO> add(CreateOrUpdateHamburguerDTO Hamburguer);
    public Flux<Hamburguer> getAll(String name);
    public Mono<Hamburguer> get(String id);
}
