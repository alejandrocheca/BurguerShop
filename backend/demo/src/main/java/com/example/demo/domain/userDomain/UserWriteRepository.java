package com.example.demo.domain.userDomain;
import com.example.demo.functionalInterfaces.ExistsByField;
import com.example.demo.functionalInterfaces.FindById;

import reactor.core.publisher.Mono;
import java.util.UUID;

public interface UserWriteRepository extends FindById<User, UUID>, ExistsByField{
    public Mono<User> save(User user, Boolean isNew);
    public Mono<User> findUserByEmail(String email);
}
