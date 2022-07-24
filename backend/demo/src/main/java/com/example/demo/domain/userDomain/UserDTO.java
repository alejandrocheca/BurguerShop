package com.example.demo.domain.userDomain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private Role role;
}
