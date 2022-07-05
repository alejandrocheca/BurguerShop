package com.example.demo.application.ingredientApplication;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import com.example.demo.application.domain.ingredientDomain.IngredientType;

@Getter
@Setter
public class CreateOrUpdateIngredientDTO {
    private String name;
    private BigDecimal price;
    private IngredientType type;  
}
