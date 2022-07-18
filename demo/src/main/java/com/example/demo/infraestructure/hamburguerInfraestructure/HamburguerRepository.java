package com.example.demo.infraestructure.hamburguerInfraestructure;
import com.example.demo.domain.hamburguerDomain.Hamburguer;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Repository
public interface HamburguerRepository {
    @Query("{ ?0: { $exists: true } }")
    Mono<Integer> existsByField(String name);
}
