package com.example.demo.core;
import com.example.demo.core.exceptions.BadRequestException;
import com.example.demo.core.exceptions.NotFoundException;
import com.example.demo.core.functionalInterfaces.FindById;
import java.util.UUID;
import reactor.core.publisher.Mono;

public class ApplicationBase {
    private FindById<T, UUID> getById;

    protected ApplicationBase(FindById<T, UUID> getById) {
        this.getById = getById;
    }
    public static UUID getUUIDfrom(String id) throws IllegalArgumentException {
        return UUID.fromString(id);
    }
    protected Mono<T> findById(UUID id) {
        return this.getById
                        .findById(id)
                        .switchIfEmpty(Mono.error(new NotFoundException(
                            String.format("No item found for id %s", id.toString())
                        )));
    }
    protected Mono<T> findById(String id) {
        try {
            return this.findById(ApplicationBase.getUUIDfrom(id));
        } catch (Exception exception) {
            BadRequestException badRequest = new BadRequestException();
            badRequest.addException("Bad Request", "String failed to convert into UUID");
            badRequest.addException("In particular", exception.getMessage());
            return Mono.error(badRequest);
        }
    }
}
