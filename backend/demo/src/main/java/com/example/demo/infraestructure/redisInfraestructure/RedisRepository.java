package com.example.demo.infraestructure.redisInfraestructure;
import com.example.demo.core.exceptions.RedisConnectionException;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Mono;
import java.time.Duration;

public class RedisRepository implements RedisRepositoryInterface<T,ID>{
    private final ReactiveRedisOperations<ID, T> redisOperations;

    public RedisRepository(final ReactiveRedisOperations<ID, T> redisOperations){
        this.redisOperations = redisOperations;
    }
    public Mono<T> getFromID(ID id) {
        return redisOperations
                    .opsForValue()
                    .get(id)
                    .onErrorResume(err -> Mono.error(new RedisConnectionException(err.getMessage())));
    }
    public Mono<T> set(ID id, T t, long hours) {
        return redisOperations
                    .opsForValue()
                    .set(id, t, Duration.ofHours(hours))
                    .then(Mono.just(t));
    }
    public Mono<Boolean> removeFromID(ID id) {
        return redisOperations
                    .opsForValue()
                    .delete(id)
                    .onErrorResume(err -> Mono.error(new RedisConnectionException(err.getMessage())));
    }
}
