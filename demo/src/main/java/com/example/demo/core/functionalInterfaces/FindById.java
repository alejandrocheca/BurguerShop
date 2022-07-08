package com.example.demo.core.functionalInterfaces;

import reactor.core.publisher.Mono;

public interface FindById {
    public Mono<T> findById(Id id);
}
