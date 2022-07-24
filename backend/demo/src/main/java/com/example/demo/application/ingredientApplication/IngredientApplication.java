package com.example.demo.application.ingredientApplication;
import com.example.demo.domain.ingredientDomain.Ingredient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IngredientApplication {
    public Mono<IngredientDTO> add(CreateOrUpdateIngredientDTO dto);
    public Mono<IngredientDTO> get(String id);
    public Mono<Void> update(String id, CreateOrUpdateIngredientDTO dto);
    public Mono<Void> delete(String id);
    public Flux<Ingredient> getAll(String name);
}
