package com.example.demo.application.hamburguerApplication;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import com.example.demo.domain.ingredientDomain.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class hamburguerDTO {
    private UUID id;
    private String name;
    private String image;
    private BigDecimal price;
    private Set<Ingredient> Ingredients = new HashSet<Ingredient>();   
}
