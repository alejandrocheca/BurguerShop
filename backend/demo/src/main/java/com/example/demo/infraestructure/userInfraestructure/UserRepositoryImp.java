package com.example.demo.infraestructure.userInfraestructure;
import com.example.demo.domain.userDomain.*;
import com.example.demo.exceptions.NotFoundException;


import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepositoryImp implements UserWriteRepository{
    private final UserRepository userRepository;

    @Override
    public Mono<User> findById(UUID id) {
        return this.userRepository.findById(id);
    }
    public Mono<User> findUserByEmail(String email) {
        return this.userRepository
                        .findUserByEmail(email)
                        .switchIfEmpty(Mono.error(new NotFoundException(
                            String.format("No user found with email %s", email)
                        )));
    }
    @Override
    public Mono<User> save(User user, Boolean isNew) {
        user.setThisNew(isNew);
        return this.userRepository.save(user);
    }
    @Override
    public Mono<Boolean> exists(String email) {
        return Mono.sequenceEqual(this.userRepository.existsByField(email), Mono.just(1));
    }
}
