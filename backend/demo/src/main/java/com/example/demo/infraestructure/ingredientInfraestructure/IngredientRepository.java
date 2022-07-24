package com.example.demo.infraestructure.ingredientInfraestructure;
import com.example.demo.domain.ingredientDomain.Ingredient;
import com.example.demo.domain.ingredientDomain.IngredientProjection;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface IngredientRepository extends ReactiveMongoRepository<Ingredient, UUID>{
    @Query("{ name : /$0/}")
    Flux<IngredientProjection> findByCriteria(String name);
    @Query("{$0: {$exists: true } }")
    Mono<Ingredient> existsByField(String name);
}
