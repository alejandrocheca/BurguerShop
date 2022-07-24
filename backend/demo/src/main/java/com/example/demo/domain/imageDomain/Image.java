package com.example.demo.domain.imageDomain;
import  com.example.demo.core.EntityBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image extends EntityBase{
    @NotEmpty
    private byte[] content;
}
