package com.example.demo.application.hamburguerApplication;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CreateOrUpdateHamburguerDTO {
    @NotBlank
    private String name;
    @NonNull
    private String image;
    @NotEmpty
    private Set<UUID> ingredients = new HashSet<UUID>();        
}