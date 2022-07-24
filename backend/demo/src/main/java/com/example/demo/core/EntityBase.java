package com.example.demo.core;
import com.example.demo.core.exceptions.BadRequestException;
import com.example.demo.core.functionalInterfaces.ExistsByField;

import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
public abstract class EntityBase implements Persistable<UUID>, Serializable{
    @Id
    private UUID id;
    @Transient
    private boolean isThisNew = false;
    
    @Override
    public boolean isNew() {
        return this.isThisNew();
    }
    public void getDeclaredFieldsFrom(Object source){
        Field sourceField;
        for (Field myField: this.getClass().getDeclaredFields()) {
            try {
                sourceField = source.getClass().getDeclaredField(myField.getName());
                sourceField.setAccessible(true);
                if (null != sourceField.get(source)) {
                    myField.setAccessible(true);
                    myField.set(this, sourceField.get(source));
                }
            } catch (Exception ex){
            }
        }
    }
    public void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<EntityBase>> violations = validator.validate(this);
        if (!violations.isEmpty()) {
            BadRequestException badRequestException = new BadRequestException();
            for (ConstraintViolation<EntityBase> violation : violations) {
                badRequestException.addException(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw badRequestException;
        }
    }
    public Mono<Boolean> validate(String key, String value, ExistsByField existsByField) {
        this.validate();
        return existsByField.exists(value)
                            .flatMap(exists -> {
                                if (exists) {
                                    BadRequestException badRequestException = new BadRequestException();
                                    badRequestException.addException(key, String.format("value '%s' is duplicated", value));
                                    return Mono.error(badRequestException);
                                } else {
                                    return Mono.just(false);
                                }
                            });
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EntityBase)) {
            return false;
        }
        EntityBase tmpEntity = (EntityBase) obj;
        return this.id.equals(tmpEntity.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
