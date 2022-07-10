package com.example.demo.domain.HamburguerDomain;

import reactor.core.publisher.Flux;

public interface HamburguerReadRepository {
    public Flux<Hamburguer> getAll(String name);
}
