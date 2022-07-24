package com.example.demo.infraestructure.userInfraestructure;
import  com.example.demo.domain.userDomain.User;

import java.util.UUID;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, UUID>{
    @Query("{ 'email' : ?0 }")
    public Mono<User> findUserByEmail(String email);
    @Query("{ ?0: { $exists: true } }")
    public Mono<Integer> existsByField(String email);
}
