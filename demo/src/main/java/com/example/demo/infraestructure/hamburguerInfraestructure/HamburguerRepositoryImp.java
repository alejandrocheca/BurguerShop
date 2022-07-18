package com.example.demo.infraestructure.hamburguerInfraestructure;
import com.example.demo.domain.HamburguerDomain.Hamburguer;
import com.example.demo.domain.HamburguerDomain.HamburguerReadRepository;
import com.example.demo.domain.HamburguerDomain.HamburguerWriteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class HamburguerRepositoryImp implements HamburguerWriteRepository, HamburguerReadRepository{
    private final HamburguerRepository hamburguerRepository;

    @Autowired
    public HamburguerRepositoryImp(final HamburguerRepository hamburguerRepository){
        this.hamburguerRepository = hamburguerRepository;
    }

    @Override
    public Mono<Hamburguer> findById(UUID id) {
        return this.hamburguerRepository.findById(id);
    }
    @Override
    public Flux<Hamburguer> getAll(String name) {
        return this.hamburguerRepository.findAll();
    }
    @Override
    public Mono<Boolean> exists(String name) {
        return Mono.sequenceEqual(this.hamburguerRepository.existsByField(name), Mono.just(1));
    }

    @Override
    public Mono<Hamburguer> save(Hamburguer hamburguer, Boolean isNew) {
        hamburguer.setThisNew(isNew);
        return this.hamburguerRepository.save(hamburguer);
    }
}
