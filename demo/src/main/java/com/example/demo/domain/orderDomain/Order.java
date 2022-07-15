package com.example.demo.domain.orderDomain;
import  com.example.demo.core.EntityBase;
import  com.example.demo.exceptions.BadRequestException;
import  Hamburgueseria.exceptions.NotFoundExceptione;
import  com.example.demo.domain.userDomain.UserWriteRepository;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Mono;

@Data
@Document(collection = "order")
@Setter
@EqualsAndHashCode(callSuper=true)
public class Order extends EntityBase{
    @NotNull
    private UUID userId;
    @NotNull
    private UUID hamburguerId;
    @NotNull
    private OrderState state;
    @NotNull
    @NotBlank
    private String address;

    public void newCommand(UUID newCommand){
        if(state.equals(OrderState.submitted)){
            this.hamburguerId = newCommand;
            this.validate();
        } else {
            throw new BadRequestException("Unable to modify the command");
        }
    }
    public Mono<Boolean> validateUser(UserWriteRepository userRepository){
        return userRepository.findById(userId)
                    .switchIfEmpty(Mono.error(new NotFoundException("user not found in DB")))
                    .map(user -> true);
    }
    public void cancel(){
        if(state.equals(OrderState.submitted)){
            this.state = OrderState.cancelled;
        } else {
            throw new BadRequestException("The order is not cancelable");
        }
    }
}
