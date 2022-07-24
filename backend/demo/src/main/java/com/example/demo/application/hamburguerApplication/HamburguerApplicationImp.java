package com.example.demo.application.hamburguerApplication;
import com.example.demo.application.imageApplication.ImageApplication;
import com.example.demo.application.ingredientApplication.IngredientApplication;
import com.example.demo.core.ApplicationBase;
import com.example.demo.domain.ingredientDomain.Ingredient;
import com.example.demo.domain.hamburguerDomain.Hamburguer;
import com.example.demo.domain.hamburguerDomain.HamburguerReadRepository;
import com.example.demo.domain.hamburguerDomain.HamburguerWriteRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class HamburguerApplicationImp extends ApplicationBase<Hamburguer> implements HamburguerApplicatio{
    private final HamburguerWriteRepository hamburguerWriteRepository;
    private final HamburguerReadRepository hamburguerReadRepository;
    private final IngredientApplication ingredientApplication;
    private final ImageApplication imageApplication;
    private final ModelMapper modelMapper;

    @Autowired
    public HamburguerApplicationImp(final HamburguerWriteRepository hamburguerWriteRepository, 
                            final HamburguerReadRepository hamburguerReadRepository,
                            final ModelMapper modelMapper,
                            final IngredientApplication ingredientApplication,
                            final ImageApplication imageApplication){
        super((id) -> hamburguerWriteRepository.findById(id));
        this.hamburguerWriteRepository = hamburguerWriteRepository;
        this.hamburguerReadRepository = hamburguerReadRepository;
        this.ingredientApplication = ingredientApplication;
        this.imageApplication = imageApplication;
        this.modelMapper = modelMapper;
    }

    @Override
    public Mono<Hamburguer> get(String id) {
        return this.findById(id).map(dbHamburguer -> this.modelMapper.map(dbHamburguer, Hamburguer.class));
    }

    @Override
    public Flux<Hamburguer> getAll(String name) {
        return this.hamburguerReadRepository.getAll(name);
    }

 @Override
    public Mono<HamburguerDTO> add(CreateOrUpdateHamburguerDTO dto) {
        Hamburguer hamburguer = this.modelMapper.map(dto, Hamburguer.class);
        hamburguer.setId(UUID.randomUUID());
        hamburguer.setThisNew(true);
        hamburguer.validate("name", hamburguer.getName(), (name) -> this.hamburguerWriteRepository.exists(name));

        return Flux.fromIterable(dto.getIngredients())
                    .flatMap(id -> ingredientApplication.get(id.toString()))
                    .doOnNext(dbIngredient -> {
                        Ingredient ingredient = this.modelMapper.map(dbIngredient, Ingredient.class);
                        hamburguer.addIngredient(ingredient);
                    })
                    .then(hamburguerWriteRepository.save(hamburguer,true))
                    .flatMap(monoHamburguer -> {
                        log.info(this.serializeObject(hamburguer, "added"));
                        return Mono.just(this.modelMapper.map(hamburguer, HamburguerDTO.class));
                    });
    }

}
